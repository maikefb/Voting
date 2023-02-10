package com.github.voting.controller.votingagenda.v1;

import com.github.voting.dto.PageDto;
import com.github.voting.dto.PaginationDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;
import com.github.voting.service.votingagenda.v1.VotingAgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/voting-agenda")
public class VotingAgendaController implements VotingAgendaApi {

    private final VotingAgendaService votingAgendaService;

    @Override
    @PostMapping
    public void create(@RequestBody @Valid VotingAgendaCreateRequestDto requestDto) {
        votingAgendaService.create(requestDto);
    }

    @Override
    @PutMapping
    public void votingSession() {

    }

    @Override
    @GetMapping("/session-end")
    public void getSessionEnd() {

    }

    @Override
    @GetMapping("/session-open")
    public void getOpenVotingSession() {

    }

    @Override
    @GetMapping
    public PaginationDto<VotingAgendaResponseDto> getVotingSessions(String cpfCnpj, PageDto pageDto) {
       return votingAgendaService.findVotingSessions(cpfCnpj,pageDto);
    }
}
