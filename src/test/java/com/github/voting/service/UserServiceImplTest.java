package com.github.voting.service;

import com.github.voting.domain.user.User;
import com.github.voting.dto.user.v1.UserCreateRequestDto;
import com.github.voting.exception.NotFoundException;
import com.github.voting.mapper.user.v1.UserMapper;
import com.github.voting.repository.user.v1.UserRepository;
import com.github.voting.service.user.v1.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Test
    public void create_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final var requestDto = new UserCreateRequestDto("MockName", userDocument, LocalDate.of(2000, 10, 1) );
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        when(userMapper.mapNewUser(requestDto)).thenReturn(user);

        // Atc
        userService.create(requestDto);

        // Assert
        verify(userMapper).mapNewUser(requestDto);
        verify(userRepository).save(user);
    }

    @Test
    public void findByDocument_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        when(userRepository.findBycpfCnpj(userDocument)).thenReturn(Optional.of(user));

        // Atc
        var actual = userService.findByDocument(userDocument);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(user);
    }

    @Test
    public void findByDocument_withNotFoundException(){
        // Arrange
        final String userDocument = "99999999999";
        when(userRepository.findBycpfCnpj(userDocument)).thenReturn(Optional.empty());
        final String messageError = "Usuario não encontrado!";

        // Atc
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.findByDocument(userDocument));

        // Assert
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }


}
