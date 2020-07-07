package com.example.concetto.controllers.v1;

import com.example.concetto.api.v1.model.SubjectDTO;
import com.example.concetto.exception.DataIntegrityError;
import com.example.concetto.models.Subject;
import com.example.concetto.models.User;
import com.example.concetto.services.ConceptService;
import com.example.concetto.services.SubjectService;
import com.example.concetto.services.UserService;
import com.example.concetto.utility.AuthUtility;
import com.example.concetto.utility.DateUtility;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController()
@RequestMapping("/api/v1/subject")
public class SubjectController {
    private final static String DONE = "done";
    private final static String SAVE = "save";

    private ConceptService conceptService;
    private SubjectService subjectService;
    private UserService userService;
    private AuthUtility authUtility;
    private DateUtility dateUtility;

    public SubjectController(SubjectService subjectService, UserService userService, AuthUtility authUtility, ConceptService conceptService, DateUtility dateUtility) {
        this.conceptService = conceptService;
        this.subjectService = subjectService;
        this.userService = userService;
        this.authUtility = authUtility;
        this.dateUtility = dateUtility;
    }

    @PutMapping
    public SubjectDTO saveSubject(@RequestBody Subject subject, OAuth2Authentication authentication){
        User user = userService.getUserByEmail(authUtility.getEmail(authentication));
        subject.setUser(user);
        subject.setLastUpdate(dateUtility.addDaysToDate(new Date(), -1));

        SubjectDTO savedSubjectDTO = subjectService.save(subject);
        return savedSubjectDTO;
    }

    @GetMapping("/all")
    List<SubjectDTO> getAllSubjectsByUserId(OAuth2Authentication authentication, @RequestParam(value="s", required=false) String sortParam){
        User user = userService.getUserByEmail(authUtility.getEmail(authentication));
        List<Subject> subjects = subjectService.findAllWhereLastUpdateIsInPast(user.getId());

        if(subjects != null) {
            subjects.stream().forEach(subject -> {
                Integer reviewCount = conceptService.reviewCountBySubjectId(subject.getId());
                Date mostRecentNextReviewDate =  conceptService.findMostRecentNextReviewDate(subject.getId());

                subject.setNextReviewDate(mostRecentNextReviewDate);
                subject.setReviewCount(reviewCount);
            });
            subjectService.saveAll(subjects);
        }

        List<SubjectDTO> subjectDTOS;

        if(sortParam == null){
            subjectDTOS = subjectService.findAllDtoByUserId(user.getId());
        }else{
            switch(sortParam.toLowerCase()){
                case DONE:
                    subjectDTOS = subjectService.findAllSubjectByUserIdAndDone(user.getId());
                    break;
                case SAVE:
                    subjectDTOS = subjectService.findAllSubjectByUserIdAndSaved(user.getId());
                    break;
                default:
                    //TODO: throw error...
                    throw new DataIntegrityError("Given sort param does not exist.");
            }
        }

        return subjectDTOS;
    }



    @GetMapping("/{id}")
    SubjectDTO getSubjectById(@PathVariable Long id){
        return subjectService.findById(id);
    }

}
