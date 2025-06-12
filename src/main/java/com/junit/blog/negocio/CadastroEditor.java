package com.junit.blog.negocio;

import java.util.Objects;

import com.junit.blog.armazenamento.ArmazenamentoEditor;
import com.junit.blog.exception.EditorNaoEncontradoException;
import com.junit.blog.exception.RegraNegocioException;
import com.junit.blog.modelo.Editor;

public class CadastroEditor {

    private final ArmazenamentoEditor armazenamentoEditor;
    private final GerenciadorEnvioEmail gerenciadorEnvioEmail;

    public CadastroEditor(ArmazenamentoEditor armazenamentoEditor, GerenciadorEnvioEmail gerenciadorEnvioEmail) {
        this.armazenamentoEditor = armazenamentoEditor;
        this.gerenciadorEnvioEmail = gerenciadorEnvioEmail;
    }

    public Editor criar(Editor editor) {
        Objects.requireNonNull(editor);

        verificarSeExisteEditorUsandoMesmoEmail(editor);
        editor = armazenamentoEditor.salvar(editor);
        enviarEmailDeCadastro(editor);

        return editor;
    }

    public Editor editar(Editor editorAtualizado) {
        Objects.requireNonNull(editorAtualizado);

        verificarSeExisteEditorUsandoMesmoEmailComIdDiferente(editorAtualizado);
        Editor editor = encontrarEditor(editorAtualizado);
        editor.atualizarComDados(editorAtualizado);

        return armazenamentoEditor.salvar(editor);
    }

    public void remover(Long editorId) {
        Objects.requireNonNull(editorId);
        armazenamentoEditor.encontrarPorId(editorId).orElseThrow(EditorNaoEncontradoException::new);
        armazenamentoEditor.remover(editorId);
    }

    private void verificarSeExisteEditorUsandoMesmoEmail(Editor editor) {
        if(armazenamentoEditor.encontrarPorEmail(editor.getEmail()).isPresent()) {
            throw new RegraNegocioException("Já existe um editor com esse e-mail " + editor.getEmail());
        }
    }

    private Editor encontrarEditor(Editor editorAtualizado) {
        return armazenamentoEditor.encontrarPorId(editorAtualizado.getId())
                .orElseThrow(EditorNaoEncontradoException::new);
    }

    private void verificarSeExisteEditorUsandoMesmoEmailComIdDiferente(Editor editorAtualizado) {
        if(armazenamentoEditor.encontrarPorEmailComIdDiferenteDe(
                editorAtualizado.getEmail(),
                editorAtualizado.getId()).isPresent()) {
            throw new RegraNegocioException("Já existe um editor com esse e-mail " + editorAtualizado.getEmail());
        }
    }

    private void enviarEmailDeCadastro(Editor editor) {
        Mensagem mensagem = new Mensagem(editor.getEmail(), "Novo cadastro", "Seu cadastro foi concluído");
        gerenciadorEnvioEmail.enviarEmail(mensagem);
    }
}
