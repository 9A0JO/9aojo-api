package br.com.fiap.abctechapi.application.impl;

import br.com.fiap.abctechapi.application.AssistanceApplication;
import br.com.fiap.abctechapi.application.dto.AssistDto;
import br.com.fiap.abctechapi.application.dto.AssistFormDto;
import br.com.fiap.abctechapi.handler.exception.IdNotFoundException;
import br.com.fiap.abctechapi.model.Assistance;
import br.com.fiap.abctechapi.service.AssistanceService;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class AssistanceApplicationImpl implements AssistanceApplication {
    private final AssistanceService assistanceService;

    public AssistanceApplicationImpl(AssistanceService assistanceService) {
        this.assistanceService = assistanceService;
    }

    @Override
    public List<AssistDto> getAssists() {
        List<Assistance> list = assistanceService.getAssistanceList();
        return AssistDto.converter(list);
    }

    @Override
    public AssistDto getAssistById(Long id) {
        try {
            Assistance assistance = assistanceService.getAssistanceById(id);
            return new AssistDto(assistance);
        }
        catch (EntityNotFoundException e) {
            throw new IdNotFoundException("Id invalid", "id não encontrado na base de dados");
        }

    }
    @Override
    public AssistDto createAssist(AssistFormDto assistDto) {
        Assistance assistance = new Assistance();
        assistance.setName(assistDto.getName());
        assistance.setDescription(assistDto.getDescription());
        assistanceService.saveAssist(assistance);
        return new AssistDto(assistance);
    }
}
