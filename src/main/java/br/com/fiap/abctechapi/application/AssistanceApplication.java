package br.com.fiap.abctechapi.application;

import br.com.fiap.abctechapi.application.dto.AssistDto;
import br.com.fiap.abctechapi.application.dto.AssistFormDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface AssistanceApplication {
    List<AssistDto> getAssists();
    AssistDto getAssistById(Long id) ;
    AssistDto createAssist(AssistFormDto assistDto);
}
