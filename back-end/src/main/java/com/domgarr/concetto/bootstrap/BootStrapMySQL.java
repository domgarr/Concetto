package com.domgarr.concetto.bootstrap;

import com.domgarr.concetto.repositories.ConceptRepository;
import com.domgarr.concetto.models.Concept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@Profile({"dev", "prod"})
//If i wanted to use a com.example.concetto.bootstrap for an in-memory DB such as h2, I could use the default profile.
public class BootStrapMySQL implements ApplicationListener<ContextRefreshedEvent> {
    private final ConceptRepository conceptRepository;

    public BootStrapMySQL(ConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    private void loadConcepts() {
        Concept concept1 = new Concept();
        concept1.setName("REST");
        concept1.setExplanation("Explain what REST is.");
        conceptRepository.save(concept1);
    }

    //This will be called on start-up because the context is being initialized an when refreshed.
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {


        //
        if (conceptRepository.count() == 0) {
            //loadConcepts();
            log.debug("Loading concepts.");
        }
    }
}
