package br.com.fiap.abctechapi.application.dto;

import br.com.fiap.abctechapi.model.Assistance;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AssistDto {
    private Long id;
    private String name;
    private String description;

    public AssistDto(Assistance assistance) {
        this.id = assistance.getId();
        this.name = assistance.getName();
        this.description = assistance.getDescription();
    }
    public static List<AssistDto> converter(List<Assistance> assists) {
        return assists.stream().map(AssistDto::new).collect(Collectors.toList());
    }
    public static Page<AssistDto> converter(Page<Assistance> assists) {
        return assists.map(AssistDto::new);
    }

}
