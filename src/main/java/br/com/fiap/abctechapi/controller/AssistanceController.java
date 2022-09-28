package br.com.fiap.abctechapi.controller;

import br.com.fiap.abctechapi.application.AssistanceApplication;
import br.com.fiap.abctechapi.application.dto.AssistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assistance")
public class AssistanceController {
    private AssistanceApplication assistApplication;
    public AssistanceController(@Autowired AssistanceApplication assistApplication) {
        this.assistApplication = assistApplication;
    }
    @GetMapping
    public ResponseEntity<List<AssistDto>> getAssists() {
        List<AssistDto> list = assistApplication.getAssists();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AssistDto> getAssistById(@PathVariable Long id) {
        AssistDto assistDto = assistApplication.getAssistById(id);
        return ResponseEntity.ok(assistDto);
    }

}
