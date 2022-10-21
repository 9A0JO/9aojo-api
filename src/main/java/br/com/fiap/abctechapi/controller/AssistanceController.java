package br.com.fiap.abctechapi.controller;

import br.com.fiap.abctechapi.application.AssistanceApplication;
import br.com.fiap.abctechapi.application.dto.AssistDto;
import br.com.fiap.abctechapi.application.dto.AssistFormDto;
import br.com.fiap.abctechapi.model.Assistance;
import br.com.fiap.abctechapi.repository.AssistanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
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

    @GetMapping("/paginacao")
    public ResponseEntity<Page<AssistDto>> findAll(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao
    ) {
        Page<AssistDto> lista = assistApplication.getAssistsPage(paginacao);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssistDto> getAssistById(@PathVariable Long id) {
        AssistDto assistDto = assistApplication.getAssistById(id);
        return ResponseEntity.ok(assistDto);
    }
    @PostMapping()
    public ResponseEntity<AssistDto> createAssist(@RequestBody @Valid AssistFormDto assistFormDto, UriComponentsBuilder uriBuilder) {
        AssistDto assistDto = assistApplication.createAssist(assistFormDto);
        URI uri = uriBuilder.path("/assistance/{id}").buildAndExpand(assistDto.getId()).toUri();
        return ResponseEntity.created(uri).body(assistDto);
    }

}
