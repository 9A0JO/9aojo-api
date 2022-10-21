package br.com.fiap.abctechapi.service;

import br.com.fiap.abctechapi.model.Assistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AssistanceService {
    List<Assistance> getAssistanceList();
    Page<Assistance> getAssistanceListPages(Pageable pageable);
    Assistance getAssistanceById(Long id);
    void saveAssist(Assistance assistance);
}
