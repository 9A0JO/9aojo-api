package br.com.fiap.abctechapi.application;

import br.com.fiap.abctechapi.application.dto.AssistDto;
import br.com.fiap.abctechapi.application.dto.AssistFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssistanceApplication {
    List<AssistDto> getAssists();
    Page<AssistDto> getAssistsPage(Pageable pageable);
    AssistDto getAssistById(Long id) ;
    AssistDto createAssist(AssistFormDto assistDto);
}
