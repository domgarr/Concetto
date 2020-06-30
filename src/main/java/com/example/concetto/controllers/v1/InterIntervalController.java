package com.example.concetto.controllers.v1;

import com.example.concetto.models.Concept;
import com.example.concetto.models.InterInterval;
import com.example.concetto.models.Subject;
import com.example.concetto.services.ConceptService;
import com.example.concetto.services.InterIntervalService;
import com.example.concetto.services.SubjectService;
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
    private final SubjectService subjectService;

    public InterIntervalController(InterIntervalService interIntervalService, ConceptService conceptService, SubjectService subjectService) {
        this.interIntervalService = interIntervalService;
        this.conceptService = conceptService;
        this.subjectService = subjectService;
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

        Long subjectId = conceptService.findSubjectIdByInterIntervalId(updatedInterInterval.getId());
        subjectService.decrementReviewCount(subjectId);

        return interIntervalService.save(updatedInterInterval);
    }
}
