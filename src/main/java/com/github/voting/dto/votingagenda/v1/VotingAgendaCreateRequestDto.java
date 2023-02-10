package com.github.voting.dto.votingagenda.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "New Voting Agenda", description = "Request to new Voting Agenda")
public class VotingAgendaCreateRequestDto {

    @NotEmpty
    @Size(max = 50)
    @ApiModelProperty(notes = "Title the Voting Agenda", example = "Leisure area renovation", required = true)
    private String title;

    @NotEmpty
    @Size(max = 255)
    @ApiModelProperty(notes = "description the Voting Agenda", example = "We must reform the leisure area for the reasons...", required = true)
    private String description;

    @NotEmpty
    @Size(min = 11,max = 18)
    @ApiModelProperty(notes = "User CPF/CNPJ", example = "999.999.999-99", required = true)
    private String userDocument;
}
