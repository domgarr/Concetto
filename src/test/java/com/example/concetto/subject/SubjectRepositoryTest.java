package com.example.concetto.subject;

import com.example.concetto.api.v1.mapper.SubjectMapper;
import com.example.concetto.api.v1.mapper.UserMapper;
import com.example.concetto.api.v1.model.SubjectDTO;
import com.example.concetto.models.Subject;
import com.example.concetto.models.User;
import com.example.concetto.repositories.SubjectRepository;
import com.example.concetto.repositories.UserRepository;
import com.example.concetto.services.SubjectService;
import com.example.concetto.services.SubjectServiceImpl;
import com.example.concetto.services.UserService;
import com.example.concetto.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SubjectRepositoryTest {
    public static final String ASD_GMAIL_COM = "asd@gmail.com";
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    private  UserService userService;
    private  SubjectService subjectService;

    @BeforeEach
    void init(){
        userService = new UserServiceImpl(UserMapper.INSTANCE, userRepository );
        subjectService = new SubjectServiceImpl(SubjectMapper.INSTANCE, subjectRepository);
    }

    @Test
    void findAllSubjectByUserId_GivenOneSubjectWithUserIdOne_ReturnListWithOneSubject(){
        User user = new User();
        user.setEmail(ASD_GMAIL_COM);
        User savedUser = userService.save(user);

        Subject subject = new Subject();
        subject.setUser(savedUser);
        subject.setName("Java Spring");

        SubjectDTO savedSubject = subjectService.save(subject);

        List<SubjectDTO> subjectDtoList = subjectService.findAllByUserId(savedUser.getId());
        assertEquals(1, subjectDtoList.size());
    }

}
