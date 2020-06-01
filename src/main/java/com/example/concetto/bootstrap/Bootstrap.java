package com.example.concetto.bootstrap;

import com.example.concetto.models.Concept;
import com.example.concetto.repositories.ConceptRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *  Only called when no profile is set. Used for populating the H2 database with Concepts.
 */
@Component
@Profile("default")
public class Bootstrap implements CommandLineRunner {
    private ConceptRepository conceptRepository;

    public Bootstrap(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Concept annotation = new Concept();
        annotation.setName("Annotation");

        Concept rest = new Concept();
        rest.setName("Rest");

        conceptRepository.save(annotation);
        conceptRepository.save(rest);

    }
}
