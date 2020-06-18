package com.example.concetto;

import com.example.concetto.api.v1.mapper.ConceptMapper;
import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.models.Concept;
import com.example.concetto.models.Subject;
import com.example.concetto.models.User;
import com.example.concetto.repositories.ConceptRepository;
import com.example.concetto.repositories.SubjectRepository;
import com.example.concetto.repositories.UserRepository;
import com.example.concetto.services.ConceptService;
import com.example.concetto.services.ConceptServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
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

    @BeforeEach
    void init() {
        conceptService = new ConceptServiceImpl(ConceptMapper.INSTANCE, conceptRepository);
    }


    //TODO: The following should only be instantiated once.
    @Test
    public void getAllConceptsByUserId_WithUserIdOne_CountIsOne() {
        User user = new User();
        user.setEmail("asd@gmail.com");
        User userSaved = userRepository.save(user);
        assertNotNull(userSaved);

        Optional<User> optionalUser = userRepository.findByEmail("asd@gmail.com");

        Subject subject = new Subject();
        subject.setName("Web");
        subject.setUser(userSaved);

        Subject savedSubject = subjectRepository.save(subject);

        Concept concept = new Concept();
        concept.setSubject(savedSubject);
        concept.setName("REST");
        concept.setExplanation("Blah");
        concept.setUser(optionalUser.get());

        Concept conceptSaved = conceptRepository.save(concept);
        assertNotNull(conceptSaved);
        assertEquals(userSaved.getId(), concept.getUser().getId());

        List<ConceptDTO> conceptDTOS = conceptService.getAllConceptsByUserId(userSaved.getId());
        assertEquals(1, conceptDTOS.size());
    }
}
