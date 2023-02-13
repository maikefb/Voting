package com.github.voting.mapper.voting.v1;

import com.github.voting.domain.user.User;
import com.github.voting.domain.voting.Voting;
import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.voting.v1.VotingCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class VotingMapper {

    public Voting mapNewVoting(VotingCreateRequestDto requestDto, VotingAgenda votingAgenda, User user){
        return Voting.builder()
                .votingAgenda(votingAgenda)
                .user(user)
                .wasApproved(requestDto.getWasApproved())
                .build();
    }

}
