package com.github.voting.controller.votingagenda.v1;

import com.github.voting.dto.PageDto;
import com.github.voting.dto.PaginationDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaStartRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(value = "/v1/voting-agenda", tags = "VotingAgenda")
public interface VotingAgendaApi  {

    @ApiOperation(value = "Create New Voting Agenda", tags = "VotingAgenda")
    void create(@RequestBody @Valid VotingAgendaCreateRequestDto requestDto);

    @ApiOperation(value = "Start a voting session", tags = "VotingAgenda")
    void votingSession(@PathVariable Long id, VotingAgendaStartRequestDto requestDto);

    @ApiOperation(value = "Get session result", tags = "VotingAgenda")
    void getSessionEnd();

    @ApiOperation(value = "Get open voting sessions", tags = "VotingAgenda")
    void getOpenVotingSession();

    @ApiOperation(value = "Find my voting sessions", tags = "VotingAgenda")
    public PaginationDto<VotingAgendaResponseDto> getVotingSessions(String cpfCnpj, PageDto pageDto);
}
