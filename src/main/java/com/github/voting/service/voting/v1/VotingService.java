package com.github.voting.service.voting.v1;

import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.voting.v1.VotingCreateRequestDto;

public interface VotingService {

    void create(VotingCreateRequestDto requestDto);

    boolean isApproved(VotingAgenda votingAgenda);
}
