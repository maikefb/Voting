package com.github.voting.repository.votingagenda.v1;

import com.github.voting.domain.votingagenda.VotingAgenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingAgendaRepository extends JpaRepository<VotingAgenda, Long> {
    
}
