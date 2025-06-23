package com.junit.blog.negocio;

import java.math.BigDecimal;

import com.junit.blog.modelo.Editor;

public class EditorTestData {

    private EditorTestData() {}

    public static Editor umEditoNovo() {
        return new Editor(null, "joao", "joao@email.com", BigDecimal.TEN, true);
    }

    public static Editor umEditorExistente() {
        return new Editor(1L, "joao", "joao@email.com", BigDecimal.TEN, true);
    }

    public static Editor umEditorComIdInexistente() {
        return new Editor(99L, "joao", "joao@email.com", BigDecimal.TEN, true);
    }
}
