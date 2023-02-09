package com.github.voting.controller.user.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/v1/user", tags = "Users")
public interface UserApi {

    @ApiOperation(value = "Create New User", tags = "Users")
    void create();





}
