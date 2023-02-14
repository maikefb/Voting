package com.github.voting.feing;

import com.github.voting.dto.userinfo.UserInfoResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserInfoClient {

    @GetMapping("https://user-info.herokuapp.com/users/{cpf}")
    UserInfoResponseDto getUserStatus(@RequestParam("cpf") String cpf);
}
