package com.junit.blog.armazenamento;

import java.util.List;
import java.util.Optional;

import com.junit.blog.modelo.Post;

/*
 * Local onde são armazenados os posts
 */
public interface ArmazenamentoPost {
    Post salvar(Post post);
    Optional<Post> encontrarPorId(Long post);
    void remover(Long postId);
    List<Post> encontrarTodos();
}
