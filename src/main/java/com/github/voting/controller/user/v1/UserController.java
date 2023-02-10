package com.github.voting.controller.user.v1;

import com.github.voting.dto.user.v1.UserCreateRequestDto;
import com.github.voting.service.user.v1.UserService;
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

    private final UserService userService;

    @Override
    @PostMapping
    public void create(@RequestBody @Valid UserCreateRequestDto requestDto) {
        userService.create(requestDto);
    }
}
