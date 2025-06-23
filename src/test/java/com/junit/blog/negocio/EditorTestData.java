package com.junit.blog.negocio;

import java.math.BigDecimal;

import com.junit.blog.modelo.Editor;

public class EditorTestData {

    private EditorTestData() {}

    public static Editor.Builder umEditorNovo() {
        return Editor.builder()
                .withNome("joao")
                .withEmail("joao@email.com")
                .withValorPagoPorPalavra(BigDecimal.TEN)
                .withPremium(true);
    }

    public static Editor.Builder umEditorExistente() {
        return umEditorNovo().withId(1L);
    }

    public static Editor.Builder umEditorComIdInexistente() {
        return umEditorNovo().withId(99L);
    }
}
