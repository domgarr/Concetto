package com.example.concetto.controllers.v1;

import com.example.concetto.api.v1.model.SubjectDTO;
import com.example.concetto.models.Subject;
import com.example.concetto.models.User;
import com.example.concetto.services.ConceptService;
import com.example.concetto.services.SubjectService;
import com.example.concetto.services.UserService;
import com.example.concetto.utility.AuthUtility;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/subject")
public class SubjectController {
    ConceptService conceptService;
    SubjectService subjectService;
    UserService userService;
    AuthUtility authUtility;

    public SubjectController(SubjectService subjectService, UserService userService, AuthUtility authUtility, ConceptService conceptService) {
        this.conceptService = conceptService;
        this.subjectService = subjectService;
        this.userService = userService;
        this.authUtility = authUtility;
    }

    @PutMapping
    public SubjectDTO saveSubject(@RequestBody Subject subject, OAuth2Authentication authentication){
        User user = userService.getUserByEmail(authUtility.getEmail(authentication));
        subject.setUser(user);
        SubjectDTO savedSubjectDTO = subjectService.save(subject);
        return savedSubjectDTO;
    }

    @GetMapping("/all")
    List<SubjectDTO> getAllSubjectsByUserId(OAuth2Authentication authentication){
        User user = userService.getUserByEmail(authUtility.getEmail(authentication));
        List<Subject> subjects = subjectService.findAllWhereLastUpdateIsInPast(user.getId());

        if(subjects != null) {
            subjects.stream().forEach(subject -> {
                Integer reviewCount = conceptService.reviewCountBySubjectId(subject.getId());
                subject.setReviewCount(reviewCount);
            });
            subjectService.saveAll(subjects);
        }

        List<SubjectDTO> subjectsDto = subjectService.findAllDtoByUserId(user.getId());
        return subjectsDto;
    }

    @GetMapping("/{id}")
    SubjectDTO getSubjectById(@PathVariable Long id){
        return subjectService.findById(id);
    }

}
