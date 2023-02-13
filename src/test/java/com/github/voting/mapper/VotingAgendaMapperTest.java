package com.github.voting.mapper;

import com.github.voting.domain.user.User;
import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaStartRequestDto;
import com.github.voting.mapper.votingagenda.v1.VotingAgendaMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class VotingAgendaMapperTest {

    @InjectMocks
    VotingAgendaMapper votingAgendaMapper;

    @Test
    public void mapNewVotingAgenda_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final var requestDto = new VotingAgendaCreateRequestDto("MockTitle", "MockDescripyion", userDocument);
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var expected = VotingAgenda.builder()
                .title("MockTitle")
                .description("MockDescripyion")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .build();

        // Atc
        final var actual = votingAgendaMapper.mapNewVotingAgenda(requestDto, user);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("createdAt", "updatedAt")
                .isEqualTo(expected);
    }

    @Test
    public void mapVotingTime_withVotingTime_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final var requestDto = new VotingAgendaStartRequestDto(30);
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var actual = VotingAgenda.builder()
                .title("MockTitle")
                .description("MockDescripyion")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .build();

        final var expected = VotingAgenda.builder()
                .title("MockTitle")
                .description("MockDescripyion")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .votingTime(30)
                .startVote(LocalDateTime.now())
                .finalizeVote(LocalDateTime.now().plusMinutes(30))
                .build();

        // Atc
        votingAgendaMapper.mapVotingTime(actual, requestDto);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(expected);
    }

    @Test
    public void mapVotingTime_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final var requestDto = new VotingAgendaStartRequestDto();
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var actual = VotingAgenda.builder()
                .title("MockTitle")
                .description("MockDescripyion")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .build();

        final var expected = VotingAgenda.builder()
                .title("MockTitle")
                .description("MockDescripyion")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .votingTime(1)
                .startVote(LocalDateTime.now())
                .finalizeVote(LocalDateTime.now().plusMinutes(1))
                .build();

        // Atc
        votingAgendaMapper.mapVotingTime(actual, requestDto);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(expected);
    }


    @Test
    public void mapVotingAgendaResponseDtoList_withSuccess(){
        // Arrange
        final String userDocument = "99999999999";
        final var user = new User(30L, userDocument, "MockName", LocalDate.of(2000, 10, 1), LocalDateTime.now(), LocalDateTime.now());
        final var votingAgendaOne = VotingAgenda.builder()
                .id(1L)
                .title("MockTitleOne")
                .description("MockDescripyionOne")
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .build();
        final var votingAgendaTwo = VotingAgenda.builder()
                .id(2L)
                .title("MockTitleTwo")
                .description("MockDescripyionTwo")
                .user(user)
                .startVote(LocalDateTime.now().minusHours(1))
                .finalizeVote(LocalDateTime.now().minusMinutes(10))
                .wasApproved(TRUE)
                .wasCounted(TRUE)
                .build();
        final var votingAgendaThree = VotingAgenda.builder()
                .id(3L)
                .title("MockTitleThree")
                .description("MockDescripyionThree")
                .user(user)
                .startVote(LocalDateTime.now().minusHours(1))
                .finalizeVote(LocalDateTime.now().plusMinutes(10))
                .wasApproved(FALSE)
                .wasCounted(TRUE)
                .build();
        final var votingAgendaList = List.of(votingAgendaOne, votingAgendaTwo, votingAgendaThree);

        final var votingAgendaDtoOne = VotingAgendaResponseDto.builder()
                .id(1L)
                .title("MockTitleOne")
                .description("MockDescripyionOne")
                .startVote(null)
                .finalizeVote(null)
                .wasCounted(FALSE)
                .wasApproved(null)
                .build();

        final var votingAgendaDtoTwo = VotingAgendaResponseDto.builder()
                .id(2L)
                .title("MockTitleTwo")
                .description("MockDescripyionTwo")
                .startVote(LocalDateTime.now().minusHours(1))
                .finalizeVote(LocalDateTime.now().minusMinutes(10))
                .wasCounted(TRUE)
                .wasApproved(TRUE)
                .build();

        final var votingAgendaDtoThree = VotingAgendaResponseDto.builder()
                .id(3L)
                .title("MockTitleThree")
                .description("MockDescripyionThree")
                .startVote(LocalDateTime.now().minusHours(1))
                .finalizeVote(LocalDateTime.now().plusMinutes(10))
                .wasCounted(TRUE)
                .wasApproved(FALSE)
                .build();
        final var expected = List.of(votingAgendaDtoOne, votingAgendaDtoTwo, votingAgendaDtoThree);


        // Atc
        final var actual = votingAgendaMapper.mapVotingAgendaResponseDtoList(votingAgendaList);

        // Assert
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(LocalDateTime.class)
                .isEqualTo(expected);
    }


}
