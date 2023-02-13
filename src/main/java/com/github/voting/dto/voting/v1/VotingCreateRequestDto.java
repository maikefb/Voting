package com.github.voting.dto.voting.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "New Voting", description = "Request to new Voting")
public class VotingCreateRequestDto {

    @NotEmpty
    @Size(min = 11,max = 18)
    @ApiModelProperty(notes = "User CPF/CNPJ", example = "999.999.999-99", required = true)
    public String userDocument;

    @NotNull
    @Min(1)
    @ApiModelProperty(notes = "Voting Agenda Id", example = "1", required = true)
    public Long votingAgendaId;

    @NotNull
    @ApiModelProperty(notes = "approved Voting Agenda", example = "false", required = true)
    public Boolean wasApproved;
}
