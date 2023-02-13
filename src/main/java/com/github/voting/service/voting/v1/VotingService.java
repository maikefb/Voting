package com.github.voting.service.voting.v1;

import com.github.voting.dto.voting.v1.VotingCreateRequestDto;

public interface VotingService {

    public void create(VotingCreateRequestDto requestDto);
}
