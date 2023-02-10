package com.github.voting.controller.votingagenda.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/v1/voting-agenda", tags = "VotingAgenda")
public interface VotingAgendaApi  {

    @ApiOperation(value = "Create New Voting Agenda", tags = "VotingAgenda")
    void create();

    @ApiOperation(value = "Start a voting session", tags = "VotingAgenda")
    void votingSession();

    @ApiOperation(value = "Get session result", tags = "VotingAgenda")
    void getSessionEnd();

    @ApiOperation(value = "Get open voting sessions", tags = "VotingAgenda")
    void getOpenVotingSession();
}
