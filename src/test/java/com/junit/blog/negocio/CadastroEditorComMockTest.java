package com.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junit.blog.armazenamento.ArmazenamentoEditor;
import com.junit.blog.modelo.Editor;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditorComMockTest {

    Editor editor;
    
    @Mock // a cada novo teste instânciado um novo mock - não haverá problema de um teste interferir no outro
    ArmazenamentoEditor armazenamentoEditor;
    
    @Mock
    GerenciadorEnvioEmail gerenciadorEnvioEmail;
    
    @InjectMocks // inteja os dois mocks acima para o CadastroEditor; cria uma nova instância a cada novo teste
    CadastroEditor cadastroEditor;

    @BeforeEach
    void beforeEach() {
        editor = new Editor(null, "joao", "joao@email.com", BigDecimal.TEN, true);
        Mockito.when(armazenamentoEditor.salvar(editor))
                .thenAnswer(invocacao -> {
                    Editor editorPassado = (Editor) invocacao.getArgument(0);
                    editorPassado.setId(1L);
                    return editorPassado;
                });
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
        Editor editorSalvo = cadastroEditor.criar(editor);
        assertEquals(1L, editorSalvo.getId());
    }
}
