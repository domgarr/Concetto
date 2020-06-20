package com.example.concetto.controllers.v1;

import com.example.concetto.api.v1.mapper.ConceptMapper;
import com.example.concetto.api.v1.mapper.UserMapper;
import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.api.v1.model.ConceptListDTO;
import com.example.concetto.api.v1.model.UserDTO;
import com.example.concetto.models.Concept;
import com.example.concetto.exception.DataIntegrityError;
import com.example.concetto.models.Subject;
import com.example.concetto.models.User;
import com.example.concetto.repositories.ConceptRepository;
import com.example.concetto.repositories.UserRepository;
import com.example.concetto.services.*;
import com.example.concetto.utility.AuthUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/api/v1/concept")
@Slf4j
public class ConceptController {
    private final ConceptService conceptService;
    private final UserService userService;
    private final SubjectService subjectService;


    public ConceptController(ConceptService conceptService, UserService userService, SubjectService subjectService) {
        this.conceptService = conceptService;
        this.userService = userService;
        this.subjectService = subjectService;
    }

    //TODO: Add error handling
    @GetMapping("/all")
    public ResponseEntity<ConceptListDTO> getAllConcepts(OAuth2Authentication authentication) {
        User user = userService.getUserByEmail(AuthUtility.getEmail(authentication));
        return new ResponseEntity<ConceptListDTO>(new ConceptListDTO(conceptService.getAllConceptsByUserId(user.getId())), HttpStatus.OK);
    }

    //TODO: Add error handling
    @GetMapping("user/{id}")
    public ResponseEntity<ConceptDTO> getConceptByUserId(@PathVariable Long id) {
        return new ResponseEntity<ConceptDTO>(conceptService.findById(id), HttpStatus.OK);
    }

    //TODO: Add error handling
    @GetMapping("/subject/{id}")
    public ResponseEntity<List<ConceptDTO>> getConceptsBySubjectId(@PathVariable Long id) {
        return new ResponseEntity<List<ConceptDTO>>(conceptService.findAllConceptsBySubjectId(id), HttpStatus.OK);
    }

    @PostMapping("/reviewed")
    public ResponseEntity<String> conceptReviewed(@PathVariable Long id){
        //TODO: Ensure the user owns the Concept.

        //TODO: Calculate next review date using Fibonacci sequence.

        return null;
    }

    @Transactional
    @PutMapping
    public ResponseEntity<Concept> saveConcept(@RequestBody Concept concept, OAuth2Authentication authentication) {
        User user = userService.getUserByEmail(AuthUtility.getEmail(authentication));
        concept.setUser(user);

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

        subjectService.incrementCount(concept.getSubject().getId());
        return new ResponseEntity<Concept>(conceptService.save(concept), HttpStatus.OK);
    }
}
