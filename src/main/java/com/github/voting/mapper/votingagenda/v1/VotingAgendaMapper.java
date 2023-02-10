package com.github.voting.mapper.votingagenda.v1;

import com.github.voting.domain.user.User;
import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;
import liquibase.repackaged.org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.nonNull;

@Component
public class VotingAgendaMapper {

    public VotingAgenda mapNewVotingAgenda(VotingAgendaCreateRequestDto requestDto, User user){
        return VotingAgenda.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .build();
    }

    public List<VotingAgendaResponseDto> mapVotingAgendaResponseDtoList(List<VotingAgenda> entityList){
        return Optional.ofNullable(entityList)
                .filter(CollectionUtils::isNotEmpty)
                .map(this::mapToListDto)
                .orElse(null);
    }

    private List<VotingAgendaResponseDto> mapToListDto(List<VotingAgenda> entityList){
        return entityList.stream()
                .map(this::mapVotingAgendaResponseDto)
                .collect(Collectors.toList());
    }

    private VotingAgendaResponseDto mapVotingAgendaResponseDto(VotingAgenda votingAgenda){
        return VotingAgendaResponseDto.builder()
                .id(votingAgenda.getId())
                .title(votingAgenda.getTitle())
                .description(votingAgenda.getDescription())
                .startVote(nonNull(votingAgenda.getStartVote()) ? votingAgenda.getStartVote() : null)
                .finalizeVote(nonNull(votingAgenda.getFinalizeVote()) ? votingAgenda.getFinalizeVote() : null)
                .wasCounted(votingAgenda.getWasCounted())
                .wasApproved(votingAgenda.getWasCounted() ? votingAgenda.getWasApproved() : null)
                .build();
    }
}
