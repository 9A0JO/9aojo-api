package br.com.fiap.abctechapi.service;

import br.com.fiap.abctechapi.handler.exception.IdNotFoundException;
import br.com.fiap.abctechapi.model.Assistance;
import br.com.fiap.abctechapi.repository.AssistanceRepository;
import br.com.fiap.abctechapi.service.impl.AssistanceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AssistanceServiceTest {
    @Mock
    private AssistanceRepository assistanceRepository;
    private AssistanceService assistanceService;

    private final Long ID = 1L;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        assistanceService = new AssistanceServiceImpl(assistanceRepository);
    }

    @DisplayName("Listando assistencias disponíveis :: sucesso")
    @Test
    public void list_success() {
        Assistance assistance1 = new Assistance(1L, "Mock Assistance 1", "Description 1");
        Assistance assistance2 = new Assistance(1L, "Mock Assistance 2", "Description 2");

        when(assistanceRepository.findAll()).thenReturn(List.of(assistance1, assistance2));
        List<Assistance> values = assistanceService.getAssistanceList();
        Assertions.assertEquals(values.size(), 2);
        Assertions.assertSame(values.get(0), assistance1);
        Assertions.assertSame(values.get(1), assistance2);
    }

    @DisplayName("Listando assistencias indisponíveis :: erro")
    @Test
    public void list_error() {
        when(assistanceRepository.findAll()).thenReturn(List.of());
        List<Assistance> values = assistanceService.getAssistanceList();
        Assertions.assertEquals(0, values.size());
    }

    @DisplayName("Obter assistance pelo id :: success")
    @Test
    public void get_assistance_by_id_success() {
        Assistance assistance1 = new Assistance(1L, "Mock Assistance 1", "Description 1");
        when(assistanceRepository.getReferenceById(ID)).thenReturn(assistance1);
        Assistance result = assistanceService.getAssistanceById(ID);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Assistance.class, result.getClass());
        Assertions.assertEquals(assistance1.getId(), result.getId());
    }

    @DisplayName("Obter assistance pelo id :: error")
    @Test
    public void get_assistance_by_id_error() {
        when(assistanceRepository.getReferenceById(2L)).thenThrow(new IdNotFoundException("Id invalid", "id não encontrado na tabela assistances"));
        Assertions.assertThrows(IdNotFoundException.class, () -> assistanceService.getAssistanceById(2L));
        try {
            assistanceService.getAssistanceById(2L);
        }
        catch (Exception e) {
             Assertions.assertEquals(IdNotFoundException.class, e.getClass());
             Assertions.assertEquals("Id invalid", e.getMessage());
        }

    }
}
