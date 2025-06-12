package com.junit.blog.armazenamento;

import java.util.List;
import java.util.Optional;

import com.junit.blog.modelo.Editor;
/*
 * Local onde são armazenados os editores
 */
public interface ArmazenamentoEditor {
    Editor salvar(Editor editor);
    Optional<Editor> encontrarPorId(Long editor);
    Optional<Editor> encontrarPorEmail(String email);
    Optional<Editor> encontrarPorEmailComIdDiferenteDe(String email, Long id);
    void remover(Long editorId);
    List<Editor> encontrarTodos();
}
