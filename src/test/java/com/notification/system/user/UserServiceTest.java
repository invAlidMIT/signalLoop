package com.notification.system.user;

import com.notification.system.auth.enums.Role;
import com.notification.system.user.dto.UserRequestDTO;
import com.notification.system.user.dto.UserResponseDTO;
import com.notification.system.user.entity.User;
import com.notification.system.user.exception.EmailExistException;
import com.notification.system.user.mapper.UserMapper;
import com.notification.system.user.repository.UserRepository;
import com.notification.system.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
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

    @Test
    void shouldCreateUserSuccessfully() {

        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("amit@test.com");
        request.setPassword("password");

        User user = new User();
        User savedUser = new User();
        savedUser.setId(1L);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(1L);

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

        UserResponseDTO result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        assertEquals(Role.ROLE_USER, user.getRole());
        assertEquals("encodedPassword", user.getPassword());

        verify(userRepository).findByEmail(request.getEmail());
        verify(passwordEncoder).encode(request.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("amit@test.com");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(new User()));

        assertThrows(
                EmailExistException.class,
                () -> userService.createUser(request)
        );

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllUsers() {

        User user1 = new User();
        User user2 = new User();

        UserResponseDTO dto1 = new UserResponseDTO();
        UserResponseDTO dto2 = new UserResponseDTO();

        when(userRepository.findAll())
                .thenReturn(List.of(user1, user2));

        when(userMapper.toResponse(user1))
                .thenReturn(dto1);

        when(userMapper.toResponse(user2))
                .thenReturn(dto2);

        List<UserResponseDTO> result = userService.getUsers();

        assertEquals(2, result.size());

        verify(userRepository).findAll();
    }

    @Test
    void shouldCreateAdminSuccessfully() {

        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("admin@test.com");

        User user = new User();
        User savedUser = new User();
        UserResponseDTO response = new UserResponseDTO();

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        when(userMapper.toEntity(request))
                .thenReturn(user);

        when(userRepository.save(user))
                .thenReturn(savedUser);

        when(userMapper.toResponse(savedUser))
                .thenReturn(response);

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encodedPassword");

        UserResponseDTO result = userService.createAdmin(request);

        assertNotNull(result);

        assertEquals(Role.ROLE_ADMIN, user.getRole());

        verify(passwordEncoder).encode(request.getPassword());

        assertEquals("encodedPassword", user.getPassword());

        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenAdminEmailAlreadyExists() {

        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("admin@test.com");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(new User()));

        assertThrows(
                EmailExistException.class,
                () -> userService.createAdmin(request)
        );

        verify(userRepository, never()).save(any());
    }

}
