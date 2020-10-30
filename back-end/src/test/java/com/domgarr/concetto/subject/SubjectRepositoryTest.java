package com.domgarr.concetto.subject;

import com.domgarr.concetto.api.v1.mapper.SubjectMapper;
import com.domgarr.concetto.api.v1.mapper.UserMapper;
import com.domgarr.concetto.api.v1.model.SubjectDTO;
import com.domgarr.concetto.models.Subject;
import com.domgarr.concetto.models.User;
import com.domgarr.concetto.repositories.SubjectRepository;
import com.domgarr.concetto.repositories.UserRepository;
import com.domgarr.concetto.services.SubjectService;
import com.domgarr.concetto.services.SubjectServiceImpl;
import com.domgarr.concetto.services.UserService;
import com.domgarr.concetto.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//TODO: Switching to using Postman for Data Driven API Tests.
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

    private User user;
    private Subject subject;
    /*
    @BeforeEach
    void init(){
        userService = new UserServiceImpl(UserMapper.INSTANCE, userRepository );
        subjectService = new SubjectServiceImpl(SubjectMapper.INSTANCE, subjectRepository);

        user = new User();
        user.setEmail(ASD_GMAIL_COM);
        User savedUser = userService.save(user);

        subject = new Subject();
        subject.setUser(savedUser);
        subject.setName("Java Spring");
    }

    @Test
    void findAllSubjectByUserId_GivenOneSubjectWithUserIdOne_ReturnListWithOneSubject(){
        User savedUser = userService.save(user);
        SubjectDTO savedSubject = subjectService.save(subject);

        List<SubjectDTO> subjectDtoList = subjectService.findAllDtoByUserId(savedUser.getId());
        assertEquals(1, subjectDtoList.size());
    }

    @Test
    void findUserIdById_GivenCorrectId_ReturnId(){
        User savedUser = userService.save(user);
        SubjectDTO savedSubject = subjectService.save(subject);

        Long userId = subjectRepository.findUserIdById(savedSubject.getId());
        assertNotNull(userId);
    }

    @Test
    void findUserIdById_GivenAnNonExisitantId_ReturnNotFoundException(){
        User savedUser = userService.save(user);
        SubjectDTO savedSubject = subjectService.save(subject);

        Long id = subjectRepository.findUserIdById(20L);
        assertNull(id);

    }
    */


}
