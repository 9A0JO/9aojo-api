package br.com.fiap.abctechapi.repository;

import br.com.fiap.abctechapi.handler.exception.IdNotFoundException;
import br.com.fiap.abctechapi.model.Assistance;
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
public class AssistanceRepositoryTest {
    @Mock
    private AssistanceRepository assistanceRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Listando assistence do repositório :: success")
    @Test
    public void get_list_assistance_success() {
        Assistance assistance = new Assistance();
        when(assistanceRepository.findAll()).thenReturn(List.of(assistance));
        List<Assistance> list = assistanceRepository.findAll();
        Assertions.assertEquals(1, list.size());
    }

    @DisplayName("Obtendo assistence do repositório pelo id :: success")
    @Test
    public void get_assistance_by_id_success() {
        Assistance assistance = new Assistance();
        when(assistanceRepository.getReferenceById(assistance.getId())).thenReturn(assistance);
        Assistance result = assistanceRepository.getReferenceById(assistance.getId());
        Assertions.assertEquals(assistance.getId(), result.getId());
        Assertions.assertEquals(assistance.getClass(), result.getClass());
    }

    @DisplayName("Salvando uma assistence no repositório :: success")
    @Test
    public void create_assistance() {
        Assistance assistance = new Assistance();
        assistance.setId(123L);
        assistanceRepository.save(assistance);
        verify(assistanceRepository, times(1)).save(assistance);
    }

    @DisplayName("Listando assistence indisponíveis do repositório :: success")
    @Test
    public void list_empty() {
        when(assistanceRepository.findAll()).thenReturn(List.of());
        List<Assistance> values = assistanceRepository.findAll();
        Assertions.assertEquals(0, values.size());
    }

    @DisplayName("Obtendo assistance do repositório pelo id :: error")
    @Test
    public void get_assistance_by_id_error() {
        when(assistanceRepository.getReferenceById(2L)).thenThrow(new IdNotFoundException("Id invalid", "id não encontrado na base de dados"));
        Assertions.assertThrows(IdNotFoundException.class, () -> assistanceRepository.getReferenceById(2L));
        try {
            assistanceRepository.getReferenceById(2L);
        } catch (Exception e) {
            Assertions.assertEquals(IdNotFoundException.class, e.getClass());
            Assertions.assertEquals("Id invalid", e.getMessage());
        }
    }


}
