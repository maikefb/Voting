package com.github.voting.service.userinfo;

import com.github.voting.exception.BusinessException;
import com.github.voting.feing.UserInfoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.github.voting.enumeration.UserStatus.UNABLE_TO_VOTE;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private static final String ERROR_USER_UNABLE_TO_VOTE = "O usuário não tem permição para votar nessa pauta!";

    private final UserInfoClient userInfoClient;

    @Override
    public void validateUserStatus(String userDocument) {
        var userStatus = userInfoClient.getUserStatus(userDocument);
        if (UNABLE_TO_VOTE.equals(userStatus.getStatus()))
            throw new BusinessException(ERROR_USER_UNABLE_TO_VOTE);
    }
}
