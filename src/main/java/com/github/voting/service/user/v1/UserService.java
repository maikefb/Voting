package com.github.voting.service.user.v1;

import com.github.voting.dto.user.v1.UserCreateRequestDto;

public interface UserService {

    void create (UserCreateRequestDto requestDto);

}
