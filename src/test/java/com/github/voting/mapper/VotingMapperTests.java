package com.github.voting.mapper;

import com.github.voting.domain.user.User;
import com.github.voting.domain.voting.Voting;
import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.voting.v1.VotingCreateRequestDto;
import com.github.voting.mapper.voting.v1.VotingMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class VotingMapperTests {

    @InjectMocks
    VotingMapper votingMapper;

    @Test
    public void mapNewVoting_withApproved_withSuccess(){
        // Arrange
        final Long votingAgendaId = 1L;
        final String userDocument = "99999999999";
        final var requestDto = new VotingCreateRequestDto(userDocument, votingAgendaId, TRUE);
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgenda = new VotingAgenda(votingAgendaId, "MockTitle", "MockDescripyion", 10, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), FALSE, FALSE, user, LocalDateTime.now(), LocalDateTime.now());
        final var expected = Voting.builder()
                .votingAgenda(votingAgenda)
                .user(user)
                .wasApproved(TRUE)
                .build();

        // Atc
        final var actual = votingMapper.mapNewVoting(requestDto, votingAgenda, user);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(expected);
    }

    @Test
    public void mapNewVoting_withDisapproved_withSuccess(){
        // Arrange
        final Long votingAgendaId = 1L;
        final String userDocument = "99999999999";
        final var requestDto = new VotingCreateRequestDto(userDocument, votingAgendaId, FALSE);
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgenda = new VotingAgenda(votingAgendaId, "MockTitle", "MockDescripyion", 10, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), FALSE, FALSE, user, LocalDateTime.now(), LocalDateTime.now());
        final var expected = Voting.builder()
                .votingAgenda(votingAgenda)
                .user(user)
                .wasApproved(FALSE)
                .build();

        // Atc
        final var actual = votingMapper.mapNewVoting(requestDto, votingAgenda, user);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(expected);
    }

}
