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
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AssistanceServiceTest {

    @Mock
    private AssistanceService assistanceService;

    private final Long ID = 1L;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Listando assistence disponíveis de service:: success")
    @Test
    public void list_service_success() {
        Assistance assistance1 = new Assistance(1L, "Mock Assistance 1", "Description 1");
        Assistance assistance2 = new Assistance(1L, "Mock Assistance 2", "Description 2");

        when(assistanceService.getAssistanceList()).thenReturn(List.of(assistance1, assistance2));
        List<Assistance> values = assistanceService.getAssistanceList();
        Assertions.assertEquals(values.size(), 2);
        Assertions.assertSame(values.get(0), assistance1);
        Assertions.assertSame(values.get(1), assistance2);
    }

    @DisplayName("Listando assistence indisponíveis de service :: success")
    @Test
    public void list_empty() {
        when(assistanceService.getAssistanceList()).thenReturn(List.of());
        List<Assistance> values = assistanceService.getAssistanceList();
        Assertions.assertEquals(0, values.size());
    }

    @DisplayName("Obter assistance pelo id de service:: success")
    @Test
    public void get_assistance_by_id_success() {
        Assistance assistance = new Assistance(1L, "Mock Assistance 1", "Description 1");
        when(assistanceService.getAssistanceById(ID)).thenReturn(assistance);
        Assistance result = assistanceService.getAssistanceById(ID);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(Assistance.class, result.getClass());
        Assertions.assertEquals(assistance.getId(), result.getId());
    }

    @DisplayName("Obter assistance pelo id de service :: error")
    @Test
    public void get_assistance_by_id_error() {
        when(assistanceService.getAssistanceById(2L)).thenThrow(new IdNotFoundException("Id invalid", "id não encontrado na base de dados"));
        Assertions.assertThrows(IdNotFoundException.class, () -> assistanceService.getAssistanceById(2L));
        try {
            assistanceService.getAssistanceById(2L);
        }
        catch (Exception e) {
             Assertions.assertEquals(IdNotFoundException.class, e.getClass());
             Assertions.assertEquals("Id invalid", e.getMessage());
        }
    }
    @DisplayName("Salvando uma assistence no service :: success")
    @Test
    public void create_assistance() {
        Assistance assistance = new Assistance();
        assistance.setId(123L);
        assistanceService.saveAssist(assistance);
        verify(assistanceService, times(1)).saveAssist(assistance);
    }

}
