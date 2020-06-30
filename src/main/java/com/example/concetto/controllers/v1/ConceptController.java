package com.example.concetto.controllers.v1;

import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.api.v1.model.ConceptListDTO;
import com.example.concetto.exception.ForbiddenAccessError;
import com.example.concetto.models.Concept;
import com.example.concetto.exception.DataIntegrityError;
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
    public ResponseEntity<List<ConceptDTO>> getConceptsBySubjectId(@PathVariable Long id, @RequestParam(value = "is_scheduled", required = false, defaultValue = "false") boolean scheduled) {
        if(scheduled){
            //Return Concepts that are ready to be reviewed.
            return new ResponseEntity<>(conceptService.findAllConceptsBySubjectIdScheduledForReview(id), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(conceptService.findAllConceptsBySubjectId(id), HttpStatus.OK);
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

            return new ResponseEntity<>(conceptService.save(existingConcept), HttpStatus.OK);
        }else{
            throw new ForbiddenAccessError("Forbidden action. The resource acted upon does not belong to the user.");
        }
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Concept> saveConcept(@RequestBody Concept concept, OAuth2Authentication authentication) {

        User user = userService.getUserByEmail(authUtility.getEmail(authentication));
        Long subjectUserId = subjectService.findUserIdById(concept.getSubject().getId());

        if(user.getId() != subjectUserId){
            throw new ForbiddenAccessError("The user does not have ownership of the given subject");
        }

        InterInterval interInterval = interIntervalService.save(new InterInterval());

        concept.setUser(user);
        concept.setInterInterval(interInterval);

        validateConcept(concept);

        Concept savedConcept = conceptService.save(concept);
        subjectService.incrementCount(concept.getSubject().getId());
        subjectService.incrementReviewCount(concept.getSubject().getId());


        //TODO: Re-think if ConceptDTO should be returned instead of the concept.
        return new ResponseEntity<>(savedConcept, HttpStatus.OK);
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

}
