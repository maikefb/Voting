package com.github.voting.dto.user.v1;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "New User", description = "Request to new User")
public class UserCreateRequestDto {

    @NotEmpty
    @Size(max = 255)
    @ApiModelProperty(notes = "User name", example = "Marcos", required = true)
    private String name;

    @NotEmpty
    @Size(min = 11,max = 18)
    @ApiModelProperty(notes = "User CPF/CNPJ", example = "999.999.999-99", required = true)
    private String cpfCnpj;

    @NotNull
    @ApiModelProperty(notes = "User birth date", example = "2000-10-15", required = true)
    private LocalDate birthDate;
}
