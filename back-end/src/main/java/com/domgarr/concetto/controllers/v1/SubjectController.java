package com.domgarr.concetto.controllers.v1;

import com.domgarr.concetto.api.v1.model.SubjectDTO;
import com.domgarr.concetto.exception.DataIntegrityError;
import com.domgarr.concetto.models.Subject;
import com.domgarr.concetto.models.User;
import com.domgarr.concetto.services.ConceptService;
import com.domgarr.concetto.services.SubjectService;
import com.domgarr.concetto.services.UserService;
import com.domgarr.concetto.utility.AuthUtility;
import com.domgarr.concetto.utility.DateUtility;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

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
    public SubjectDTO saveSubject(@RequestBody Subject subject, @AuthenticationPrincipal OAuth2User principal){
        User user = userService.getUserByEmail(authUtility.getEmail(principal));
        subject.setUser(user);
        subject.setLastUpdate(dateUtility.addDaysToDate(new Date(), -1));
        
        SubjectDTO savedSubjectDTO = subjectService.save(subject);
        return savedSubjectDTO;
    }

    @GetMapping("/all")
    List<SubjectDTO> getAllSubjectsByUserId(@AuthenticationPrincipal OAuth2User principal, @RequestParam(value="s", required=false) String sortParam){
        User user = userService.getUserByEmail(authUtility.getEmail(principal));
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
