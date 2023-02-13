package com.github.voting.mapper;

import com.github.voting.domain.user.User;
import com.github.voting.dto.user.v1.UserCreateRequestDto;
import com.github.voting.mapper.user.v1.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest{

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void mapNewUser_withSuccess(){
        // Arrange
        final var requestDto = new UserCreateRequestDto("MockName", "99999999999", LocalDate.of(2000, 10, 1));
        final var expected = User.builder()
                .personName("MockName")
                .cpfCnpj("99999999999")
                .birthDate(LocalDate.of(2000, 10, 1))
                .build();
        // Atc
        final var actual = userMapper.mapNewUser(requestDto);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(expected);
    }
}
