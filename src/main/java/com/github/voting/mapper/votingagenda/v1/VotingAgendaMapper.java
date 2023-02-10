package com.github.voting.mapper.votingagenda.v1;

import com.github.voting.domain.user.User;
import com.github.voting.domain.votingagenda.VotingAgenda;
import com.github.voting.dto.votingagenda.v1.VotingAgendaCreateRequestDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaResponseDto;
import com.github.voting.dto.votingagenda.v1.VotingAgendaStartRequestDto;
import liquibase.repackaged.org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.nonNull;

@Component
public class VotingAgendaMapper {

    private static final Integer DEFAULT_VOTE_TIME = 1;

    public VotingAgenda mapNewVotingAgenda(VotingAgendaCreateRequestDto requestDto, User user){
        return VotingAgenda.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .user(user)
                .wasApproved(FALSE)
                .wasCounted(FALSE)
                .build();
    }

    public void mapVotingTime(VotingAgenda entity, VotingAgendaStartRequestDto requestDto){
        entity.setVotingTime(nonNull(requestDto.votingTime) ? requestDto.getVotingTime() : DEFAULT_VOTE_TIME);
        entity.setStartVote(LocalDateTime.now());
        entity.setFinalizeVote(entity.getStartVote().plusMinutes(entity.getVotingTime()));
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

    private VotingAgendaResponseDto mapVotingAgendaResponseDto(VotingAgenda entity){
        return VotingAgendaResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startVote(nonNull(entity.getStartVote()) ? entity.getStartVote() : null)
                .finalizeVote(nonNull(entity.getFinalizeVote()) ? entity.getFinalizeVote() : null)
                .wasCounted(entity.getWasCounted())
                .wasApproved(entity.getWasCounted() ? entity.getWasApproved() : null)
                .build();
    }
}
