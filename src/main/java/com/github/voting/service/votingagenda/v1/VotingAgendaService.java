package com.github.voting.service.votingagenda.v1;

import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.PageDto;
import com.github.voting.dto.PaginationDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaStartRequestDto;

public interface VotingAgendaService {

    void create(VotingAgendaCreateRequestDto requestDto);

    PaginationDto<VotingAgendaResponseDto> findVotingSessions(String cpfCnpj, PageDto pageDto);

    void startVotingSession(Long id, VotingAgendaStartRequestDto requestDto);

    PaginationDto<VotingAgendaResponseDto> findOpenVotingSession(PageDto pageDto);

    PaginationDto<VotingAgendaResponseDto> findSessionEnd(PageDto pageDto);

    VotingAgenda findOpenVotingSessionById(Long id);
}
