package com.github.voting.mapper.user.v1;

import com.github.voting.domain.user.User;
import com.github.voting.dto.user.v1.UserCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapNewUser(UserCreateRequestDto requestDto){
        return User.builder()
                .personName(requestDto.getName())
                .cpfCnpj(requestDto.getCpfCnpj())
                .birthDate(requestDto.getBirthDate())
                .build();
    }
}
