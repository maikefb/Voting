package com.github.voting.controller.votingagenda.v1;

import com.github.voting.dto.PageDto;
import com.github.voting.dto.PaginationDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaStartRequestDto;
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
    @PutMapping("{id}")
    public void votingSession(@PathVariable Long id, VotingAgendaStartRequestDto requestDto) {
        votingAgendaService.startVotingSession(id, requestDto);
    }

    @Override
    @GetMapping("/session-end")
    public PaginationDto<VotingAgendaResponseDto> getSessionEnd(PageDto pageDto) {
        return votingAgendaService.findSessionEnd(pageDto);
    }

    @Override
    @GetMapping("/session-open")
    public PaginationDto<VotingAgendaResponseDto> getOpenVotingSession(PageDto pageDto) {
        return votingAgendaService.findOpenVotingSession(pageDto);
    }

    @Override
    @GetMapping
    public PaginationDto<VotingAgendaResponseDto> getVotingSessions(String cpfCnpj, PageDto pageDto) {
       return votingAgendaService.findVotingSessions(cpfCnpj,pageDto);
    }
}
