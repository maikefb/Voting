package com.github.voting.controller.voting.v1;

import com.github.voting.dto.voting.v1.VotingCreateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "/v1/voting", tags = "Voting")
public interface VotingApi {

    @ApiOperation(value = "Cast a vote", tags = "Voting")
    void vote(@RequestBody @Valid VotingCreateRequestDto requestDto);
}
