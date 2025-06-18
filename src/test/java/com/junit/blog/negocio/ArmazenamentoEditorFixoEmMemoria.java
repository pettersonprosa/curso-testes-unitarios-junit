package com.junit.blog.negocio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.junit.blog.armazenamento.ArmazenamentoEditor;
import com.junit.blog.modelo.Editor;

public class ArmazenamentoEditorFixoEmMemoria implements ArmazenamentoEditor {

    public boolean chamouSalvar = false;

    @Override
    public Editor salvar(Editor editor) {
        this.chamouSalvar = true;
        if (editor.getId() == null) {
            editor.setId(1L);
        }
        return editor;
    }

    @Override
    public Optional<Editor> encontrarPorId(Long editor) {
        return Optional.empty();
    }

    @Override
    public Optional<Editor> encontrarPorEmail(String email) {
        if (email.equals("joao.existe@email.com")) {
            return Optional.of(new Editor(2L, "Joao", "joao@email.com", BigDecimal.TEN, true));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Editor> encontrarPorEmailComIdDiferenteDe(String email, Long id) {
        return Optional.empty();
    }

    @Override
    public void remover(Long editorId) {

    }

    @Override
    public List<Editor> encontrarTodos() {
        return null;
    }
}
