package com.example.concetto.controllers.v1;

import com.example.concetto.api.v1.model.SubjectDTO;
import com.example.concetto.models.Subject;
import com.example.concetto.models.User;
import com.example.concetto.services.SubjectService;
import com.example.concetto.services.UserService;
import com.example.concetto.utility.AuthUtility;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/subject")
public class SubjectController {
    SubjectService subjectService;
    UserService userService;

    public SubjectController(SubjectService subjectService, UserService userService) {
        this.subjectService = subjectService;
        this.userService = userService;
    }

    @PutMapping
    public SubjectDTO saveSubject(@RequestBody Subject subject, OAuth2Authentication authentication){
        User user = userService.getUserByEmail(AuthUtility.getEmail(authentication));
        subject.setUser(user);
        SubjectDTO savedSubjectDTO = subjectService.save(subject);
        return savedSubjectDTO;
    }

    @GetMapping("/all")
    List<SubjectDTO> getAllSubjectsByUserId(OAuth2Authentication authentication){
        User user = userService.getUserByEmail(AuthUtility.getEmail(authentication));
        List<SubjectDTO> subjects = subjectService.findAllByUserId(user.getId());
        return subjects;
    }

    @GetMapping("/{id}")
    SubjectDTO getSubjectById(@PathVariable Long id){
        return subjectService.findById(id);
    }
}
