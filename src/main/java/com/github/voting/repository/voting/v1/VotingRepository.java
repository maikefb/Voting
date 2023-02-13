package com.github.voting.repository.voting.v1;

import com.github.voting.domain.voting.Voting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingRepository extends JpaRepository<Voting, Long> {

    public boolean existsByUserCpfCnpjAndVotingAgendaId(String userDocument, Long votingAgendaId);

}
