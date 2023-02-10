package com.github.voting.controller.voting.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/v1/voting", tags = "Voting")
public interface VotingApi {

    @ApiOperation(value = "Cast a vote", tags = "Voting")
    void vote();
}
