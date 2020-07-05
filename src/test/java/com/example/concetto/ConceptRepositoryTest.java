package com.example.concetto;

import com.example.concetto.api.v1.mapper.ConceptMapper;
import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.models.Concept;
import com.example.concetto.models.InterInterval;
import com.example.concetto.models.Subject;
import com.example.concetto.models.User;
import com.example.concetto.repositories.ConceptRepository;
import com.example.concetto.repositories.SubjectRepository;
import com.example.concetto.repositories.UserRepository;
import com.example.concetto.services.ConceptService;
import com.example.concetto.services.ConceptServiceImpl;
import com.example.concetto.services.InterIntervalService;
import com.example.concetto.utility.DateUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ConceptRepositoryTest {

    ConceptService conceptService; //TODO: Use DI in the future?
    @Autowired
    ConceptRepository conceptRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    InterIntervalService interIntervalService;
    @Autowired
    DateUtility dateUtility;

    User user;
    User userSaved;

    Subject subject;
    Subject savedSubject;

    InterInterval interInterval;
    Concept concept;

    @BeforeEach
    void init() {
        conceptService = new ConceptServiceImpl(ConceptMapper.INSTANCE, conceptRepository);

        user = new User();
        user.setEmail("asd@gmail.com");
        userSaved = userRepository.save(user);

        Optional<User>optionalUser = userRepository.findByEmail("asd@gmail.com");

        subject = new Subject();
        subject.setName("Web");
        subject.setUser(userSaved);

        savedSubject = subjectRepository.save(subject);

        interInterval = new InterInterval();
        InterInterval savedInterInterval = interIntervalService.save(interInterval);

        concept = new Concept();
        concept.setName("REST");
        concept.setExplanation("Blah");

        concept.setSubject(savedSubject);
        concept.setUser(optionalUser.get());
        concept.setInterInterval(savedInterInterval);
    }


    //TODO: The following should only be instantiated once.
    @Test
    public void getAllConceptsByUserId_WithUserIdOne_CountIsOne() {

        Concept conceptSaved = conceptRepository.save(concept);
        assertNotNull(conceptSaved);
        assertEquals(userSaved.getId(), concept.getUser().getId());

        List<ConceptDTO> conceptDTOS = conceptService.getAllConceptsByUserId(userSaved.getId());
        assertEquals(1, conceptDTOS.size());
    }

    @Test
    public void findMostRecentNextReviewDate_GivenAValidSubjectId_ReturnDate(){
        Concept conceptSaved = conceptRepository.save(concept);
        assertNotNull(conceptSaved);

        Date mostRecentNextReviewDate = conceptRepository.findMostRecentNextReviewDate(conceptSaved.getSubject().getId());
        assertNotNull(mostRecentNextReviewDate);
    }

    @Test
    public void findMostRecentNextReviewDate_GivenAValidSubjectId_ReturnMostRecentDateFromCollectionOfTwoConcepts(){
        Concept conceptSaved = conceptRepository.save(concept);
        concept.setNextReviewDate(dateUtility.addDaysToDate(new Date(), 1) );
        Concept conceptTwoSaved = conceptRepository.save(concept);
        //The date returned from SQL is a different format from the default format of java.util.Date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date mostRecentNextReviewDate = conceptRepository.findMostRecentNextReviewDate(conceptSaved.getSubject().getId());
        assertEquals(formatter.format(conceptSaved.getNextReviewDate()), formatter.format(mostRecentNextReviewDate));

    }
}
