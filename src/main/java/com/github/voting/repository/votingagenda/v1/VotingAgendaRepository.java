package com.github.voting.repository.votingagenda.v1;

import com.github.voting.domain.user.User;
import com.github.voting.domain.votingagenda.VotingAgenda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VotingAgendaRepository extends JpaRepository<VotingAgenda, Long> {


    Page<VotingAgenda> findAllByUser(User user, Pageable pageable);

    Page<VotingAgenda> findAllByStartVoteBeforeAndFinalizeVoteAfter(LocalDateTime startVoteDate, LocalDateTime finalizeVoteDate, Pageable pageable);

    Page<VotingAgenda> findAllByFinalizeVoteBefore(LocalDateTime finalizeVoteDate, Pageable pageable);

    @Override
    Optional<VotingAgenda> findById(Long id);
}
