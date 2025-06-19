package com.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junit.blog.armazenamento.ArmazenamentoEditor;
import com.junit.blog.exception.RegraNegocioException;
import com.junit.blog.modelo.Editor;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditorComMockTest {

    @Spy // a cada novo teste instânciado um novo Mock.spy da classe
    Editor editor = new Editor(null, "joao", "joao@email.com", BigDecimal.TEN, true);

    @Captor
    ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);
    
    @Mock // a cada novo teste instânciado um novo mock - não haverá problema de um teste interferir no outro
    ArmazenamentoEditor armazenamentoEditor;
    
    @Mock
    GerenciadorEnvioEmail gerenciadorEnvioEmail;
    
    @InjectMocks // inteja os dois mocks acima para o CadastroEditor; cria uma nova instância a cada novo teste
    CadastroEditor cadastroEditor;

    @BeforeEach
    void beforeEach() {
        Mockito.when(armazenamentoEditor.salvar(Mockito.any(Editor.class)))
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

    @Test
    void Dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento() {
        cadastroEditor.criar(editor);
        // Verifica se o método 'salvar' do armazenamento foi chamado exatamente uma vez com o objeto 'editor'
        Mockito.verify(armazenamentoEditor, Mockito.times(1))
                .salvar(Mockito.eq(editor));
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_e_lancar_exception_ao_salvar_Entao_nao_deve_enviar_email() {
        Mockito.when(armazenamentoEditor.salvar(editor))
                .thenThrow(new RuntimeException());

        assertAll("Não deve enviar e-mail quando lançar Exception do armazenamento",
                () -> assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor)),
                () -> Mockito.verify(gerenciadorEnvioEmail, Mockito.never()).enviarEmail(Mockito.any()));
    }

    @Test
    void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_com_destino_ao_editor() {
        // ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);

        Editor editorSalvo = cadastroEditor.criar(editor);;

        Mockito.verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());

        Mensagem mensagemPassada = mensagemArgumentCaptor.getValue();

        assertEquals(editorSalvo.getEmail(), mensagemPassada.getDestinatario());
    }

    @Test
    void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_verificar_o_email() {
        cadastroEditor.criar(editor);
        Mockito.verify(editor, Mockito.atLeast(1)).getEmail();
    }

    @Test
    void Dado_um_editor_com_email_existente_Quando_cadastrar_Entao_deve_lancar_exception() {
        Mockito.when(armazenamentoEditor.encontrarPorEmail("joao@email.com"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(editor));

        Editor editorComEmailExistente = new Editor(null, "joao", "joao@email.com", BigDecimal.TEN, true);
        cadastroEditor.criar(editor);
        assertThrows(RegraNegocioException.class, ()-> cadastroEditor.criar(editorComEmailExistente));
    }

}
