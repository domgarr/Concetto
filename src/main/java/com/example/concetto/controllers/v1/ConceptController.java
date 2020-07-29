package com.example.concetto.controllers.v1;

import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.api.v1.model.ConceptListDTO;
import com.example.concetto.exception.ForbiddenAccessError;
import com.example.concetto.exception.NotFoundException;
import com.example.concetto.models.Concept;
import com.example.concetto.exception.DataIntegrityError;
import com.example.concetto.models.CountPerDate;
import com.example.concetto.models.InterInterval;
import com.example.concetto.models.User;
import com.example.concetto.services.*;
import com.example.concetto.utility.AuthUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/v1/concept")
@Slf4j
public class ConceptController {
    private final ConceptService conceptService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final InterIntervalService interIntervalService;
    private final AuthUtility authUtility;

    private enum SortParam {
        is_scheduled, saved, all
    }


    public ConceptController(ConceptService conceptService, UserService userService, SubjectService subjectService, InterIntervalService interIntervalService, AuthUtility authUtility) {
        this.conceptService = conceptService;
        this.userService = userService;
        this.subjectService = subjectService;
        this.interIntervalService = interIntervalService;
        this.authUtility = authUtility;
    }

    //TODO: Add error handling
    @GetMapping("/all")
    public ResponseEntity<ConceptListDTO> getAllConcepts(OAuth2Authentication authentication) {
        User user = userService.getUserByEmail(authUtility.getEmail(authentication));
        return new ResponseEntity<>(new ConceptListDTO(conceptService.getAllConceptsByUserId(user.getId())), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConceptDTO> getConceptDtoById(@PathVariable Long id){
        return new ResponseEntity<>(conceptService.findDtoById(id), HttpStatus.OK);
    }

    //TODO: Add error handling
    @GetMapping("/subject/{id}")
    public ResponseEntity<List<ConceptDTO>> getConceptsBySubjectId(@PathVariable Long id, @RequestParam(value = "s", required = false) String sortParam, OAuth2Authentication authentication) {
        User user = userService.getUserByEmail(authUtility.getEmail(authentication));
        subjectOwnerCheck(id, user);

        if(sortParam == null){
            return new ResponseEntity<>(conceptService.findAllConceptsBySubjectId(id), HttpStatus.OK);
        }

        SortParam sortParamToEnum = SortParam.valueOf(sortParam);

        switch(sortParamToEnum){
            case is_scheduled:
                return new ResponseEntity<>(conceptService.findAllConceptsBySubjectIdScheduledForReview(id), HttpStatus.OK);
            case saved:
                return new ResponseEntity<>(conceptService.findAllBySubjectIdNotDone(id), HttpStatus.OK);
            case all:
                return new ResponseEntity<>(conceptService.findAllConceptsBySubjectId(id), HttpStatus.OK);
            default:
                throw new NotFoundException("Given param not found");
        }

    }

    @Transactional
    @PatchMapping
    public ResponseEntity<Concept> updateConcept(@RequestBody Concept concept, OAuth2Authentication authentication){
        User user = userService.getUserByEmail(authUtility.getEmail(authentication));
        Long userIdFromConcept = conceptService.findUserIdByConceptId(concept.getId());
        if(user.getId() == userIdFromConcept){
            validateConcept(concept);
            //Todo: extract into a method.
            Concept existingConcept = conceptService.findById(concept.getId());
            existingConcept.setName(concept.getName());
            existingConcept.setExplanation(concept.getExplanation());
            existingConcept.setReviewed(concept.isReviewed());
            existingConcept.setSimplified(concept.isSimplified());

            boolean isDone = existingConcept.isDone();
            Concept savedConcept = conceptService.save(existingConcept);
            if(isDone == false && savedConcept.isDone()){
                Long subjectId = conceptService.findSubjectIdById(savedConcept.getId());
                subjectService.decrementSaveCount(subjectId);
            }
            return new ResponseEntity<>(conceptService.save(existingConcept), HttpStatus.OK);
        }else{
            throw new ForbiddenAccessError("Forbidden action. The resource acted upon does not belong to the user.");
        }
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Concept> saveConcept(@RequestBody Concept concept, OAuth2Authentication authentication) {

        User user = userService.getUserByEmail(authUtility.getEmail(authentication));
        subjectOwnerCheck(concept.getSubject().getId(), user);

        InterInterval interInterval = interIntervalService.save(new InterInterval());

        concept.setUser(user);
        concept.setInterInterval(interInterval);

        validateConcept(concept);

        Concept savedConcept = conceptService.save(concept);
        subjectService.incrementCount(concept.getSubject().getId());

        if(savedConcept.isDone()){
            //If a concept is in the done state, it has been 'finished' and is ready to be studied/reviewed.
            subjectService.incrementReviewCount(concept.getSubject().getId());
            //Only need to make this call if another concept in done state is added.
            subjectService.updateNextReviewDateFromMostRecentConcept(concept.getSubject().getId());
        }else{
            subjectService.incrementSaveCount(concept.getSubject().getId());
        }

        //TODO: Re-think if ConceptDTO should be returned instead of the concept.
        return new ResponseEntity<>(savedConcept, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteConcept(@PathVariable Long id, OAuth2Authentication authentication){
        conceptOwnerCheck(id,  userService.getUserByEmail(authUtility.getEmail(authentication)));
        conceptService.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    //TODO: Look into preauthorize.
    private void conceptOwnerCheck(Long conceptId, User user){
        Long userIdFromConcept = conceptService.findUserIdByConceptId(conceptId);

        if(user.getId() != userIdFromConcept){
            throw new ForbiddenAccessError("The user does not have ownership of the given subject");
        }
    }

    private void subjectOwnerCheck(Long subjectId, User user) {
        Long userIdFromSubject = subjectService.findUserIdById(subjectId);

        if(user.getId() != userIdFromSubject){
            throw new ForbiddenAccessError("The user does not have ownership of the given subject");
        }
    }


    private void validateConcept(Concept concept) {
        Set<ConstraintViolation<Concept>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(concept);
        if (constraintViolations.size() > 0) {
            StringBuilder errorMessage = new StringBuilder();

            Iterator<ConstraintViolation<Concept>> constraintIt = constraintViolations.iterator();
            while (constraintIt.hasNext()) {
                ConstraintViolation constraintViolation = constraintIt.next();
                errorMessage.append(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage() + ". ");
            }

            //The use of substring to to remove the space from the last error message that is appended.
            throw new DataIntegrityError(errorMessage.substring(0, errorMessage.length() - 2));
        }
    }

    @GetMapping("/review-count-per-date")
    public ResponseEntity<List<CountPerDate>> getCountOfConceptsPerDate(OAuth2Authentication authentication){
        User user = userService.getUserByEmail(authUtility.getEmail(authentication));

        List<CountPerDate> countPerDate = conceptService.findConceptReviewCountPerDate(user.getId());

        return new ResponseEntity<>(countPerDate, HttpStatus.OK);
    }

}
