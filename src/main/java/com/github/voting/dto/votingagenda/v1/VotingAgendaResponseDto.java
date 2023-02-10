package com.github.voting.dto.votingagenda.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Search Voting Agenda", description = "Response to search voting agenda by user")
public class VotingAgendaResponseDto {

    @ApiModelProperty(notes = "Voting Agenda identifier", example = "123")
    public Long id;

    @ApiModelProperty(notes = "Voting Agenda title", example = "title 123")
    public String title;

    @ApiModelProperty(notes = "Voting Agenda description", example = "description 123")
    public String description;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @ApiModelProperty(notes = "Voting started", example = "2017-01-13T17:09:42.411")
    public LocalDateTime startVote;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @ApiModelProperty(notes = "Voting ended", example = "2017-01-13T17:09:45.411")
    public LocalDateTime finalizeVote;

    @ApiModelProperty(notes = "The votes have been counted", example = "false")
    public Boolean wasCounted;

    @ApiModelProperty(notes = "The agenda was approved", example = "true")
    public Boolean wasApproved;
}
