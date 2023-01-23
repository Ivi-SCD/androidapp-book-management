package br.com.alura.agenda.usecases.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.model.entities.Livro;

public class LivroDAO {

    private final static List<Livro> livros = new ArrayList<>();
    private static int contadorId = 1;

    public void salvar(Livro livro) {
        livro.setId(contadorId);
        livros.add(livro);
        contadorId++;
    }

    public List <Livro> selecionar() {
        return new ArrayList<>(livros);
    }

    public Livro buscarLivroPorId(Integer id) {
        Livro livroAchado = null;

        for (Livro l: livros) {
            if(l.getId() == id) {
                livroAchado = l;
            }
        }
        return livroAchado;
    }

    public void editar(Livro livro) {

        Livro livroEncontrado = null;

        for(Livro l : livros) {
            if(l.getId() == livro.getId()) {
                livroEncontrado = l;
            }
            if(livroEncontrado != null) {
                int posicao = livros.indexOf(livroEncontrado);
                livros.set(posicao, livro);
            }
        }
    }

    public void remover(Livro livroSelecionado) {
        Livro livroRemovido = buscarLivroPorId(livroSelecionado.getId());
        if(!(livroRemovido == null)) {
            livros.remove(livroSelecionado);
        }
    }
}
