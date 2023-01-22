package br.com.alura.agenda.usecases.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.model.entities.Livro;

public class LivroDAO {

    private final static List<Livro> livros = new ArrayList<>();

    public void salvar(Livro livro) {
        livros.add(livro);
    }

    public List <Livro> selecionar() {
        return new ArrayList<>(livros);
    }
}
