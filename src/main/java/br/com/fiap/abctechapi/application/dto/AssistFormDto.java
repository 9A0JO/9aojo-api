package br.com.fiap.abctechapi.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class AssistFormDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}
