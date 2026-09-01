package com.notification.system.user;

import com.notification.system.auth.enums.Role;
import com.notification.system.user.dto.UserRequestDTO;
import com.notification.system.user.dto.UserResponseDTO;
import com.notification.system.user.entity.User;
import com.notification.system.user.exception.EmailExistException;
import com.notification.system.user.mapper.UserMapper;
import com.notification.system.user.repository.UserRepository;
import com.notification.system.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO request;
    private UserResponseDTO response;
    private User user;
    private User savedUser;

    @BeforeEach
    public void setUp(){
        request=new UserRequestDTO();
        request.setEmail("test123@gmail.com");
        request.setPassword("password123");

        user=new User();
        savedUser=new User();
        savedUser.setId(1L);
    }

    @Test
    public void createUserSuccessfully(){


        response=new UserResponseDTO();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());
        when(userMapper.toEntity(request))
                .thenReturn(user);
        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encodedPassword");
        when(userRepository.save(user))
                .thenReturn(savedUser);
        when(userMapper.toResponse(savedUser))
                .thenReturn(response);


        UserResponseDTO result=userService.createUser(request);

        ArgumentCaptor<User>   userCaptor=
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .save(userCaptor.capture());
        User savedArgument=userCaptor.getValue();
        verify(userRepository)
                .findByEmail(request.getEmail());
        verify(userMapper)
                .toEntity(request);
        verify(passwordEncoder)
                .encode(request.getPassword());
        verify(userMapper)
                .toResponse(savedUser);

        assertEquals(Role.ROLE_USER,savedArgument.getRole());
        assertEquals("encodedPassword",savedArgument.getPassword());
        assertNotNull(result);
        assertEquals(response,result);
    }

    @Test
    void emailAddressAlreadyExist() {

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(new User()));

        EmailExistException exception = assertThrows(
                EmailExistException.class,
                () -> userService.createUser(request)
        );

        verify(userRepository)
                .findByEmail(request.getEmail());

        verify(userRepository, never())
                .save(any());

        verify(userMapper, never())
                .toEntity(any());

        verify(passwordEncoder, never())
                .encode(anyString());

        assertEquals(
                "Email already exists",
                exception.getMessage()
        );
    }
}
