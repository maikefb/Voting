package com.github.voting.service;

import com.github.voting.domain.user.User;
import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaStartRequestDto;
import com.github.voting.exception.NotFoundException;
import com.github.voting.mapper.votingagenda.v1.VotingAgendaMapper;
import com.github.voting.repository.votingagenda.v1.VotingAgendaRepository;
import com.github.voting.service.user.v1.UserService;
import com.github.voting.service.voting.v1.VotingService;
import com.github.voting.service.votingagenda.v1.VotingAgendaServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VotingAgendaServiceImplTest {

    @InjectMocks
    VotingAgendaServiceImpl votingAgendaService;

    @Mock
    VotingAgendaRepository votingAgendaRepository;

    @Mock
    VotingAgendaMapper votingAgendaMapper;

    @Mock
    UserService userService;

    @Mock
    VotingService votingService;

    @Test
    public void  create_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final var requestDto = new VotingAgendaCreateRequestDto("TitleMock", "DescriptionMock", userDocument);
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgenda = VotingAgenda.builder()
                .title("TitleMock")
                .description("DescriptionMock")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .build();

        when(userService.findByDocument(userDocument)).thenReturn(user);
        when(votingAgendaMapper.mapNewVotingAgenda(requestDto, user)).thenReturn(votingAgenda);

        // Atc
        votingAgendaService.create(requestDto);

        // Assert
        verify(votingAgendaRepository).save(votingAgenda);
    }

    @Test
    public void startVotingSession_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final var votingAgendaId = 5L;
        final var votingAgendaStartRequestDto = new VotingAgendaStartRequestDto(10);
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgenda = VotingAgenda.builder()
                .id(votingAgendaId)
                .title("TitleMock")
                .description("DescriptionMock")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .votingTime(null)
                .build();
        when(votingAgendaRepository.findById(votingAgendaId)).thenReturn(Optional.ofNullable(votingAgenda));

        // Atc
        votingAgendaService.startVotingSession(votingAgendaId, votingAgendaStartRequestDto);

        // Assert
        verify(votingAgendaMapper).mapVotingTime(votingAgenda, votingAgendaStartRequestDto);
        verify(votingAgendaRepository).save(votingAgenda);
    }

    @Test
    public void startVotingSession_withNotFoundException(){
        // Arrange
        final String messageError = "Pauta de votação não encontrada!";
        final var votingAgendaId = 5L;
        final var votingAgendaStartRequestDto = new VotingAgendaStartRequestDto(10);

        when(votingAgendaRepository.findById(votingAgendaId)).thenReturn(Optional.empty());


        // Atc
        NotFoundException exception = assertThrows(NotFoundException.class, () -> votingAgendaService.startVotingSession(votingAgendaId, votingAgendaStartRequestDto));

        // Assert
        assertThat(exception.getMessage()).isEqualTo(messageError);

    }



}
