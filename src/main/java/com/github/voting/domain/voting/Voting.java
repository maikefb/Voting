package com.github.voting.domain.voting;

import com.github.voting.domain.user.User;
import com.github.voting.domain.votingagenda.VotingAgenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Voting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "voting_agenda_id")
    private VotingAgenda votingAgenda;

    @NotNull
    private Boolean wasApproved;
}
