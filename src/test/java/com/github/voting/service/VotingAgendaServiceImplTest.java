package com.github.voting.service;

import com.github.voting.domain.user.User;
import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.PageDto;
import com.github.voting.dto.PaginationDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaStartRequestDto;
import com.github.voting.exception.NotFoundException;
import com.github.voting.mapper.votingagenda.v1.VotingAgendaMapper;
import com.github.voting.repository.votingagenda.v1.VotingAgendaRepository;
import com.github.voting.service.user.v1.UserService;
import com.github.voting.service.voting.v1.VotingService;
import com.github.voting.service.votingagenda.v1.VotingAgendaServiceImpl;
import com.github.voting.util.PaginationUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Test
    public void findOpenVotingSession_withSuccess(){
        // Arrange
        final var pageDto = PageDto.builder()
                .page(1)
                .limit(2)
                .build();
        final var user = new User(30L, "99999999989", "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgendaOne = VotingAgenda.builder()
                .id(1L)
                .title("TitleMock")
                .description("DescriptionMock")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .votingTime(null)
                .build();
        final var votingAgendaTwo = VotingAgenda.builder()
                .id(2L)
                .title("TitleMockTwo")
                .description("DescriptionMockTwo")
                .user(user)
                .wasApproved(TRUE)
                .wasCounted(TRUE)
                .votingTime(null)
                .build();
        final var votingAgendaList = List.of(votingAgendaOne, votingAgendaTwo);
        final var votingAgendaPage = new PageImpl(votingAgendaList, PageRequest.of(1, 5), 2L);
        final var votingAgendaDtoOne = VotingAgendaResponseDto.builder()
                .id(1L)
                .title("TitleMock")
                .description("DescriptionMock")
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .build();
        final var votingAgendaDtoTwo = VotingAgendaResponseDto.builder()
                .id(2L)
                .title("TitleMockTwo")
                .description("DescriptionMockTwo")
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .startVote(LocalDateTime.now().minusHours(2))
                .finalizeVote(LocalDateTime.now().minusMinutes(10))
                .build();
        final var votingAgendaDtoList = List.of(votingAgendaDtoOne, votingAgendaDtoTwo);

        final var expected = PaginationUtil.toPaginationDtoWithContentMapping(votingAgendaPage, votingAgendaDtoList);

        when(votingAgendaRepository.findAllByStartVoteBeforeAndFinalizeVoteAfter(any(), any(), any(Pageable.class))).thenReturn(votingAgendaPage);
        when(votingAgendaMapper.mapVotingAgendaResponseDtoList(votingAgendaList)).thenReturn(votingAgendaDtoList);



        // Atc
       var actual = votingAgendaService.findOpenVotingSession(pageDto);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(expected);
    }

    @Test
    public void findOpenVotingSession_withEmptyList_withSuccess(){
        // Arrange
        final var pageDto = PageDto.builder()
                .page(1)
                .limit(2)
                .build();
        final var votingAgendaList = new ArrayList<VotingAgenda>();
        final var votingAgendaPage = new PageImpl(votingAgendaList, PageRequest.of(1, 5), 2L);
        final var votingAgendaDtoList = new ArrayList<VotingAgendaResponseDto>();

        final var expected = PaginationUtil.toPaginationDtoWithContentMapping(votingAgendaPage, votingAgendaDtoList);

        when(votingAgendaRepository.findAllByStartVoteBeforeAndFinalizeVoteAfter(any(), any(), any(Pageable.class))).thenReturn(votingAgendaPage);
        when(votingAgendaMapper.mapVotingAgendaResponseDtoList(votingAgendaList)).thenReturn(votingAgendaDtoList);

        // Atc
        var actual = votingAgendaService.findOpenVotingSession(pageDto);

        // Assert
        assertThat(actual.getContent().size()).isEqualTo(0);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(expected);
    }

    @Test
    public void findSessionEnd_withSuccess(){
        // Arrange
        final var pageDto = PageDto.builder()
                .page(1)
                .limit(2)
                .build();
        final var user = new User(30L, "99999999989", "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgendaOne = VotingAgenda.builder()
                .id(1L)
                .title("TitleMock")
                .description("DescriptionMock")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .votingTime(null)
                .build();
        final var votingAgendaTwo = VotingAgenda.builder()
                .id(2L)
                .title("TitleMockTwo")
                .description("DescriptionMockTwo")
                .user(user)
                .wasApproved(TRUE)
                .wasCounted(TRUE)
                .votingTime(null)
                .build();
        final var votingAgendaNotCountedList = List.of(votingAgendaTwo);
        final var votingAgendaList = List.of(votingAgendaOne, votingAgendaTwo);
        final var votingAgendaPage = new PageImpl(votingAgendaList, PageRequest.of(1, 5), 2L);
        final var votingAgendaDtoOne = VotingAgendaResponseDto.builder()
                .id(1L)
                .title("TitleMock")
                .description("DescriptionMock")
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .build();
        final var votingAgendaDtoTwo = VotingAgendaResponseDto.builder()
                .id(2L)
                .title("TitleMockTwo")
                .description("DescriptionMockTwo")
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .startVote(LocalDateTime.now().minusHours(2))
                .finalizeVote(LocalDateTime.now().minusMinutes(10))
                .build();
        final var votingAgendaDtoList = List.of(votingAgendaDtoOne, votingAgendaDtoTwo);

        final var expected = PaginationUtil.toPaginationDtoWithContentMapping(votingAgendaPage, votingAgendaDtoList);

        when(votingAgendaRepository.findAllByFinalizeVoteBeforeAndWasCounted(any(), eq(FALSE))).thenReturn(votingAgendaNotCountedList);
        when(votingService.isApproved(votingAgendaTwo)).thenReturn(TRUE);
        when(votingAgendaRepository.findAllByFinalizeVoteBefore(any(), any(Pageable.class))).thenReturn(votingAgendaPage);
        when(votingAgendaMapper.mapVotingAgendaResponseDtoList(votingAgendaList)).thenReturn(votingAgendaDtoList);

        // Atc
        var actual = votingAgendaService.findSessionEnd(pageDto);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(expected);
    }

    @Test
    public void findVotingSessions_withSuccess(){
        // Arrange
        final var pageDto = PageDto.builder()
                .page(1)
                .limit(2)
                .build();
        final var userDocument = "99999999989";
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgendaOne = VotingAgenda.builder()
                .id(1L)
                .title("TitleMock")
                .description("DescriptionMock")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .votingTime(null)
                .build();
        final var votingAgendaTwo = VotingAgenda.builder()
                .id(2L)
                .title("TitleMockTwo")
                .description("DescriptionMockTwo")
                .user(user)
                .wasApproved(TRUE)
                .wasCounted(TRUE)
                .votingTime(null)
                .build();
        final var votingAgendaNotCountedList = List.of(votingAgendaTwo);
        final var votingAgendaList = List.of(votingAgendaOne, votingAgendaTwo);
        final var votingAgendaPage = new PageImpl(votingAgendaList, PageRequest.of(1, 5), 2L);
        final var votingAgendaDtoOne = VotingAgendaResponseDto.builder()
                .id(1L)
                .title("TitleMock")
                .description("DescriptionMock")
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .build();
        final var votingAgendaDtoTwo = VotingAgendaResponseDto.builder()
                .id(2L)
                .title("TitleMockTwo")
                .description("DescriptionMockTwo")
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .startVote(LocalDateTime.now().minusHours(2))
                .finalizeVote(LocalDateTime.now().minusMinutes(10))
                .build();
        final var votingAgendaDtoList = List.of(votingAgendaDtoOne, votingAgendaDtoTwo);

        final var expected = PaginationUtil.toPaginationDtoWithContentMapping(votingAgendaPage, votingAgendaDtoList);

        when(votingAgendaRepository.findAllByFinalizeVoteBeforeAndWasCounted(any(), eq(FALSE))).thenReturn(votingAgendaNotCountedList);
        when(votingService.isApproved(votingAgendaTwo)).thenReturn(TRUE);
        when(userService.findByDocument(userDocument)).thenReturn(user);
        when(votingAgendaRepository.findAllByUser(eq(user), any(Pageable.class))).thenReturn(votingAgendaPage);
        when(votingAgendaMapper.mapVotingAgendaResponseDtoList(votingAgendaList)).thenReturn(votingAgendaDtoList);

        // Atc
        var actual = votingAgendaService.findVotingSessions(userDocument, pageDto);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(expected);
    }
}
