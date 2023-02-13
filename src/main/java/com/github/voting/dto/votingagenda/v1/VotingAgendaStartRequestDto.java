package com.github.voting.dto.votingagenda.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Start voting session", description = "Request to start voting session")
public class VotingAgendaStartRequestDto {

    @ApiModelProperty(notes = "How long will the voting session be open", example = "60")
    public Integer votingTime;

}
