package com.github.voting.service;

import com.github.voting.dto.userinfo.UserInfoResponseDto;
import com.github.voting.exception.BusinessException;
import com.github.voting.feing.UserInfoClient;
import com.github.voting.service.userinfo.UserInfoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.github.voting.enumeration.UserStatus.ABLE_TO_VOTE;
import static com.github.voting.enumeration.UserStatus.UNABLE_TO_VOTE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserInfoServiceImplTest {

    @InjectMocks
    UserInfoServiceImpl userInfoService;

    @Mock
    UserInfoClient userInfoClient;

    @Test
    public void validateUserStatus_BusinessException(){
        // Arrange
        final String userDocument = "99999999999";
        final String messageError = "O usuário não tem permição para votar nessa pauta!";
        final var responseDto = new UserInfoResponseDto(UNABLE_TO_VOTE);

        when(userInfoClient.getUserStatus(userDocument)).thenReturn(responseDto);

        // Atc
        BusinessException exception = assertThrows(BusinessException.class, () -> userInfoService.validateUserStatus(userDocument));

        // Assert
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void validateUserStatus_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final var responseDto = new UserInfoResponseDto(ABLE_TO_VOTE);

        when(userInfoClient.getUserStatus(userDocument)).thenReturn(responseDto);

        // Atc
        userInfoService.validateUserStatus(userDocument);

    }
}
