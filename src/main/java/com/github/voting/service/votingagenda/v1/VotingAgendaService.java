package com.github.voting.service.votingagenda.v1;

import com.github.voting.dto.PageDto;
import com.github.voting.dto.PaginationDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;

public interface VotingAgendaService {

    void create(VotingAgendaCreateRequestDto requestDto);

    PaginationDto<VotingAgendaResponseDto> findVotingSessions(String cpfCnpj, PageDto pageDto);

}
