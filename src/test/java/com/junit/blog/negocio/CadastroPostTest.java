package com.junit.blog.negocio;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junit.blog.armazenamento.ArmazenamentoPost;
import com.junit.blog.modelo.Editor;
import com.junit.blog.modelo.Ganhos;
import com.junit.blog.modelo.Notificacao;
import com.junit.blog.modelo.Post;
import com.junit.blog.utilidade.ConversorSlug;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroPostTest {

    @Mock
    public ArmazenamentoPost armazenamentoPost;
    
    @Mock
    public CalculadoraGanhos calculadoraGanhos;
    
    @Mock
    public GerenciadorNotificacao gerenciadorNotificacao;

    @InjectMocks
    CadastroPost cadastroPost;

    @Captor
    ArgumentCaptor<Notificacao> notificacaoArgumentCaptor;

    @Spy
    Editor editor = EditorTestData.umEditorExistente().build();

    @Nested
    public final class Cadastro {

        @Spy
        Post post = PostTestData.umPostNovo().build();

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            cadastroPost.criar(post);

            Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_retornar_id_valido() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            Post postSalvo = cadastroPost.criar(post);

            Assertions.assertEquals(1L, postSalvo.getId());
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_retornar_post_com_ganhos() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            Mockito.when(calculadoraGanhos.calcular(post))
                    .thenReturn(new Ganhos(BigDecimal.TEN, 4, BigDecimal.valueOf(40)));

            Post postSalvo = cadastroPost.criar(post);

            Mockito.verify(post, Mockito.times(1)).setGanhos(Mockito.any(Ganhos.class));
            Assertions.assertNotNull(postSalvo.getGanhos());
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_retornar_post_com_slug() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            Post postSalvo = cadastroPost.criar(post);

            Mockito.verify(post, Mockito.times(1)).setSlug(Mockito.anyString());
            Assertions.assertNotNull(postSalvo.getSlug());
        }

        @Test
        public void Dado_um_post_null__Quanto_cadastrar__Entao_deve_lancar_exception_e_nao_deve_savar() {
            Assertions.assertThrows(NullPointerException.class, ()-> cadastroPost.criar(null));
            Mockito.verify(armazenamentoPost, Mockito.never()).salvar(Mockito.any(Post.class));
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_calcular_ganhos_antes_de_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

                cadastroPost.criar(post);

                InOrder inOrder = Mockito.inOrder(calculadoraGanhos, armazenamentoPost);
                inOrder.verify(calculadoraGanhos, Mockito.times(1)).calcular(post);
                inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_gerar_slug_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            try(MockedStatic<ConversorSlug> conversorSlug = Mockito.mockStatic(ConversorSlug.class)) {
                cadastroPost.criar(post);

                InOrder inOrder = Mockito.inOrder(armazenamentoPost, ConversorSlug.class);
                inOrder.verify(conversorSlug, () -> ConversorSlug.converterJuntoComCodigo(post.getTitulo()), Mockito.times(1));
                inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
            }
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_enviar_notificacao_apos_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            cadastroPost.criar(post);

            InOrder inOrder = Mockito.inOrder(gerenciadorNotificacao, armazenamentoPost);
            inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
            inOrder.verify(gerenciadorNotificacao, Mockito.times(1))
                    .enviar(Mockito.any(Notificacao.class));
        }

        @Test
        public void Dado_um_post_valido__Quanto_cadastrar__Entao_deve_gerar_notificacao_com_titulo_do_post() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> {
                        Post postEnviado = invocacao.getArgument(0, Post.class);
                        postEnviado.setId(1L);
                        return postEnviado;
                    });

            cadastroPost.criar(post);

            Mockito.verify(gerenciadorNotificacao)
                    .enviar(notificacaoArgumentCaptor.capture());

            Notificacao notificacao = notificacaoArgumentCaptor.getValue();
            Assertions.assertEquals("Novo post criado -> " + post.getTitulo(), notificacao.getConteudo());
        }
    }

    @Nested
    public final class Edicao {

        @Spy
        Post post = PostTestData.umPostExistente().build();

        @Test
        public void Dado_um_post_valido__Quando_editar__Entao_deve_salvar() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            cadastroPost.editar(post);

            Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
        }

        @Test
        public void Dado_um_post_valido__Quando_editar__Entao_deve_retornar_mesmo_id() {
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            Post postSalvo = cadastroPost.editar(post);

            Assertions.assertEquals(1L, postSalvo.getId());
        }

        @Test
        public void Dado_um_post_pago__Quando_editar__Entao_deve_retornar_post_com_os_mesmos_ganhos_sem_recalcular() {
            post.setConteudo("Conteúdo editado");
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            Post postSalvo = cadastroPost.editar(post);

            Mockito.verify(post, Mockito.never()).setGanhos(Mockito.any(Ganhos.class));
            Mockito.verify(post, Mockito.times(1)).isPago();
            Assertions.assertNotNull(postSalvo.getGanhos());
        }

        @Test
        public void Dado_um_post_nao_pago__Quando_editar__Entao_deve_retornar_recalcular_ganhos_antes_de_salvar() {
            post.setConteudo("Conteúdo editado");
            post.setPago(false);
            Ganhos novoGanho = new Ganhos(BigDecimal.TEN, 2, BigDecimal.valueOf(20));

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));
            Mockito.when(calculadoraGanhos.calcular(post)).thenReturn(novoGanho);

            Post postSalvo = cadastroPost.editar(post);

            Mockito.verify(post, Mockito.times(1)).setGanhos(novoGanho);
            Assertions.assertNotNull(postSalvo.getGanhos());
            Assertions.assertEquals(novoGanho, postSalvo.getGanhos());

            InOrder inOrder = Mockito.inOrder(calculadoraGanhos, armazenamentoPost);
            inOrder.verify(calculadoraGanhos, Mockito.times(1)).calcular(post);
            inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
        }

        @Test
        public void Dado_um_post_com_titulo_alterado__Quando_editar__Entao_deve_retornar_post_com_a_mesma_slug_sem_alterar() {
            post.setTitulo("Ola Teste");
            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            Post postSalvo = cadastroPost.editar(post);

            Mockito.verify(post, Mockito.never()).setSlug(Mockito.anyString());
            Assertions.assertEquals("ola-mundo-java", postSalvo.getSlug());
        }

        @Test
        public void Dado_um_post_null__Quando_editar__Entao_deve_lancar_exception_e_nao_deve_savar() {
            Assertions.assertThrows(NullPointerException.class, ()-> cadastroPost.editar(null));
            Mockito.verify(armazenamentoPost, Mockito.never()).salvar(Mockito.any(Post.class));
        }

        @Test
        public void Dado_um_post_valido__Quando_editar__Entao_deve_deve_alterar_post_salvo() {
            Post postAlterado = PostTestData.umPostExistente().build();

            Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                    .then(invocacao -> invocacao.getArgument(0, Post.class));
            Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

            cadastroPost.editar(postAlterado);

            Mockito.verify(post).atualizarComDados(postAlterado);

            InOrder inOrder = Mockito.inOrder(armazenamentoPost, post);
            inOrder.verify(post).atualizarComDados(postAlterado);
            inOrder.verify(armazenamentoPost).salvar(post);
        }

    }
}
