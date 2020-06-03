package com.example.concetto.controllers.v1;

import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.api.v1.model.ConceptListDTO;
import com.example.concetto.models.Concept;
import com.example.concetto.exception.DataIntegrityError;
import com.example.concetto.services.ConceptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

@Controller
@RequestMapping("/api/v1/concepts")
public class ConceptController {
    private final ConceptService conceptService;

    public ConceptController(ConceptService conceptService, ValidatorFactory validatorFactory) {
        this.conceptService = conceptService;
    }

    //TODO: Add error handling
    @GetMapping
    public ResponseEntity<ConceptListDTO> getAllConcepts(){
        return new ResponseEntity<ConceptListDTO>(new ConceptListDTO(conceptService.getAllConcepts()), HttpStatus.OK);
    }

    //TODO: Add error handling
    @GetMapping("user/{id}")
    public ResponseEntity<ConceptDTO> getConceptByUserId(@PathVariable Long id){
        return new ResponseEntity<ConceptDTO>(conceptService.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Concept> saveConcept(@RequestBody Concept concept) {
        Set<ConstraintViolation<Concept>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(concept);
        if(constraintViolations.size() > 0){
            StringBuilder errorMessage = new StringBuilder();
            boolean nameViolation = false; //Used when save query is set.

            Iterator<ConstraintViolation<Concept>> constraintIt =  constraintViolations.iterator();
            while(constraintIt.hasNext()){
                ConstraintViolation constraintViolation = constraintIt.next();
                errorMessage.append(constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage() + ". ");
            }
            //The use of substring to to remove the space from the last error message that is appended.
            throw new DataIntegrityError(errorMessage.substring(0, errorMessage.length() - 2));
        }
        return new ResponseEntity<Concept>(conceptService.save(concept), HttpStatus.OK);
    }
}
