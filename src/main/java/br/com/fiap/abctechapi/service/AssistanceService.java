package br.com.fiap.abctechapi.service;

import br.com.fiap.abctechapi.model.Assistance;

import java.util.List;
import java.util.Optional;

public interface AssistanceService {
    List<Assistance> getAssistanceList();
    Assistance getAssistanceById(Long id);
}
