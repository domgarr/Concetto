package com.example.concetto.controllers.v1;

import com.example.concetto.models.Concept;
import com.example.concetto.models.InterInterval;
import com.example.concetto.services.ConceptService;
import com.example.concetto.services.InterIntervalService;
import com.example.concetto.utility.IntervalUtility;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

@RestController()
@RequestMapping("/api/v1/interval")
public class InterIntervalController {
    private final InterIntervalService interIntervalService;
    private final ConceptService conceptService;

    public InterIntervalController(InterIntervalService interIntervalService, ConceptService conceptService) {
        this.interIntervalService = interIntervalService;
        this.conceptService = conceptService;
    }

    //TODO: Return a InterIntervalDTO.
    @PostMapping("/calculate")
    @Transactional
    public InterInterval calculateNextInterInterval(@RequestBody InterInterval interInterval){
        InterInterval fetchedInterInterval = interIntervalService.find(interInterval.getId());
        InterInterval updatedInterInterval = IntervalUtility.calculateNextInterval(interInterval.getResponseRating(), fetchedInterInterval);
        Concept fetchedConcept = conceptService.findByInterIntervalId(interInterval.getId());

        //Using the newly calculated length add to the nextReviewDate and update the concept.
        Date dateToUpdate = fetchedConcept.getNextReviewDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToUpdate);
        cal.add(Calendar.DATE, updatedInterInterval.getLength());
        fetchedConcept.setNextReviewDate(cal.getTime());
        conceptService.save(fetchedConcept);

        return interIntervalService.save(updatedInterInterval);
    }
}
