package com.example.concetto.controllers.v1;

import com.example.concetto.api.v1.mapper.ConceptMapper;
import com.example.concetto.api.v1.mapper.UserMapper;
import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.api.v1.model.ConceptListDTO;
import com.example.concetto.api.v1.model.UserDTO;
import com.example.concetto.models.Concept;
import com.example.concetto.exception.DataIntegrityError;
import com.example.concetto.models.User;
import com.example.concetto.repositories.ConceptRepository;
import com.example.concetto.repositories.UserRepository;
import com.example.concetto.services.ConceptService;
import com.example.concetto.services.ConceptServiceImpl;
import com.example.concetto.services.UserService;
import com.example.concetto.services.UserServiceImpl;
import com.example.concetto.utility.AuthUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.security.Principal;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/api/v1/concepts")
@Slf4j
public class ConceptController {
    private final ConceptService conceptService;
    private final UserService userService;


    public ConceptController(ConceptService conceptService, UserService userService) {
        this.conceptService = conceptService;
        this.userService = userService;
    }

    //TODO: Add error handling
    @GetMapping
    public ResponseEntity<ConceptListDTO> getAllConcepts() {
        return new ResponseEntity<ConceptListDTO>(new ConceptListDTO(conceptService.getAllConcepts()), HttpStatus.OK);
    }

    //TODO: Add error handling
    @GetMapping("user/{id}")
    public ResponseEntity<ConceptDTO> getConceptByUserId(@PathVariable Long id) {
        return new ResponseEntity<ConceptDTO>(conceptService.findById(id), HttpStatus.OK);
    }

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


        return new ResponseEntity<Concept>(conceptService.save(concept), HttpStatus.OK);
    }
}
