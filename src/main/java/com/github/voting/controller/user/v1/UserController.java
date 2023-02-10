package com.github.voting.controller.user.v1;

import com.github.voting.dto.user.UserCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController implements UserApi{

    @Override
    @PostMapping
    public void create(@RequestBody @Valid UserCreateRequestDto requestDto) {

    }
}
