package com.junit.blog.negocio;

import java.math.BigDecimal;

import com.junit.blog.modelo.Ganhos;
import com.junit.blog.modelo.Post;

public class PostTestData {

    private PostTestData() {}

    public static Post.Builder umPostNovo() {
        return Post.builder()
                .withTitulo("Olá mundo Java")
                .withConteudo("Olá Java com System.out.println")
                .withAutor(EditorTestData.umEditorExistente().build())
                .withPago(false)
                .withPublicado(true);
    }

    public static Post.Builder umPostExistente() {
        return umPostNovo()
                .withId(1L)
                .withSlug("ola-mundo-java")
                .withGanhos(new Ganhos(BigDecimal.TEN, 4, BigDecimal.valueOf(10)))
                .withPago(true);
    }

}
