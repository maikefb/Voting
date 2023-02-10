package com.github.voting.controller.user.v1;

import com.github.voting.dto.user.UserCreateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "/v1/user", tags = "Users")
public interface UserApi {

    @ApiOperation(value = "Create New User", tags = "Users")
    void create(@RequestBody @Valid UserCreateRequestDto requestDto);

}
