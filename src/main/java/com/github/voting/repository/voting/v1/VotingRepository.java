package com.github.voting.repository.voting.v1;

import com.github.voting.domain.voting.Voting;
import com.github.voting.domain.votingagenda.VotingAgenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotingRepository extends JpaRepository<Voting, Long> {

    boolean existsByUserCpfCnpjAndVotingAgendaId(String userDocument, Long votingAgendaId);

    List<Voting> findAllByVotingAgenda(VotingAgenda votingAgenda);
}
