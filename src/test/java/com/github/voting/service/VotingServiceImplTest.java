package com.github.voting.service;

import com.github.voting.domain.user.User;
import com.github.voting.domain.voting.Voting;
import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.voting.v1.VotingCreateRequestDto;
import com.github.voting.exception.BusinessException;
import com.github.voting.exception.NotFoundException;
import com.github.voting.mapper.voting.v1.VotingMapper;
import com.github.voting.repository.voting.v1.VotingRepository;
import com.github.voting.repository.votingagenda.v1.VotingAgendaRepository;
import com.github.voting.service.user.v1.UserService;
import com.github.voting.service.voting.v1.VotingServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VotingServiceImplTest {

    @InjectMocks
    VotingServiceImpl votingService;

    @Mock
    VotingRepository votingRepository;

    @Mock
    VotingAgendaRepository votingAgendaRepository;

    @Mock
    UserService userService;

    @Mock
    VotingMapper votingMapper;

    @Test
    public void create_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final Long votingAgendaId = 1L;
        final var requestDto = new VotingCreateRequestDto(userDocument, votingAgendaId, FALSE);
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgenda = new VotingAgenda(votingAgendaId, "MockTitle", "MockDescripyion", 10, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), FALSE, FALSE, user, LocalDateTime.now(), LocalDateTime.now());
        final var voting = Voting.builder()
                .votingAgenda(votingAgenda)
                .user(user)
                .wasApproved(FALSE)
                .build();

        when(votingRepository.existsByUserCpfCnpjAndVotingAgendaId(userDocument, votingAgendaId)).thenReturn(FALSE);
        when(votingAgendaRepository.findByIdAndStartVoteBeforeAndFinalizeVoteAfter(eq(votingAgendaId), any(), any())).thenReturn(Optional.of(votingAgenda));
        when(userService.findByDocument(userDocument)).thenReturn(user);
        when(votingMapper.mapNewVoting(requestDto, votingAgenda, user)).thenReturn(voting);

        // Atc
        votingService.create(requestDto);

        // Assert
        verify(votingRepository).save(voting);
    }

    @Test
    public void create_withBusinessException(){
        // Arrange
        final String userDocument = "99999999999";
        final Long votingAgendaId = 1L;
        final String messageError = "O usuário já votou nessa pauta!";
        final var requestDto = new VotingCreateRequestDto(userDocument, votingAgendaId, FALSE);
        when(votingRepository.existsByUserCpfCnpjAndVotingAgendaId(userDocument, votingAgendaId)).thenReturn(TRUE);

        // Atc
        BusinessException exception = assertThrows(BusinessException.class, () -> votingService.create(requestDto));

        // Assert
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void create_NotFoundException(){
        // Arrange
        final String userDocument = "99999999999";
        final Long votingAgendaId = 1L;
        final String messageError = "Pauta de votação não encontrada!";
        final var requestDto = new VotingCreateRequestDto(userDocument, votingAgendaId, FALSE);
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());

        when(votingRepository.existsByUserCpfCnpjAndVotingAgendaId(userDocument, votingAgendaId)).thenReturn(FALSE);
        when(votingAgendaRepository.findByIdAndStartVoteBeforeAndFinalizeVoteAfter(eq(votingAgendaId), any(), any())).thenReturn(Optional.empty());

        // Atc
        NotFoundException exception = assertThrows(NotFoundException.class, () -> votingService.create(requestDto));

        // Assert
        assertThat(exception.getMessage()).isEqualTo(messageError);
    }

    @Test
    public void isApproved_withApproved_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final Long votingAgendaId = 1L;
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgenda = new VotingAgenda(votingAgendaId, "MockTitle", "MockDescripyion", 10, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), FALSE, FALSE, user, LocalDateTime.now(), LocalDateTime.now());
        final var votingOne = Voting.builder()
                .user(user)
                .votingAgenda(votingAgenda)
                .wasApproved(TRUE)
                .build();
        final var votingTwo = Voting.builder()
                .user(user)
                .votingAgenda(votingAgenda)
                .wasApproved(TRUE)
                .build();
        final var votings = List.of(votingOne, votingTwo);

        when(votingRepository.findAllByVotingAgenda(votingAgenda)).thenReturn(votings);

        // Atc
        var actual = votingService.isApproved(votingAgenda);

        // Assert
        assertThat(TRUE).isEqualTo(actual);
    }

    @Test
    public void isApproved_withDisapproved_withSuccess() {
        // Arrange
        final String userDocument = "99999999999";
        final Long votingAgendaId = 1L;
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgenda = new VotingAgenda(votingAgendaId, "MockTitle", "MockDescripyion", 10, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), FALSE, FALSE, user, LocalDateTime.now(), LocalDateTime.now());
        final var votingOne = Voting.builder()
                .user(user)
                .votingAgenda(votingAgenda)
                .wasApproved(FALSE)
                .build();
        final var votingTwo = Voting.builder()
                .user(user)
                .votingAgenda(votingAgenda)
                .wasApproved(FALSE)
                .build();
        final var votings = List.of(votingOne, votingTwo);

        when(votingRepository.findAllByVotingAgenda(votingAgenda)).thenReturn(votings);

        // Atc
        var actual = votingService.isApproved(votingAgenda);

        // Assert
        assertThat(FALSE).isEqualTo(actual);
    }

    @Test
    public void isApproved_withStalemate_withSuccess() {
        // Arrange
        final String userDocument = "99999999999";
        final Long votingAgendaId = 1L;
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgenda = new VotingAgenda(votingAgendaId, "MockTitle", "MockDescripyion", 10, LocalDateTime.now(), LocalDateTime.now().plusMinutes(10), FALSE, FALSE, user, LocalDateTime.now(), LocalDateTime.now());
        final var votingOne = Voting.builder()
                .user(user)
                .votingAgenda(votingAgenda)
                .wasApproved(TRUE)
                .build();
        final var votingTwo = Voting.builder()
                .user(user)
                .votingAgenda(votingAgenda)
                .wasApproved(FALSE)
                .build();
        final var votings = List.of(votingOne, votingTwo);

        when(votingRepository.findAllByVotingAgenda(votingAgenda)).thenReturn(votings);

        // Atc
        var actual = votingService.isApproved(votingAgenda);

        // Assert
        assertThat(FALSE).isEqualTo(actual);
    }



}
