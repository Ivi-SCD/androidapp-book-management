package br.com.alura.agenda.model.entities;

import java.text.ParseException;

public class LivroFactory {

    private Livro livro = new Livro();

    public LivroFactory() {
    }

    public void comNome(String nome) {
        livro.setNome(nome);
    }

    public void comAutor(String autor) {
        livro.setAutor(autor);
    }

    public void comPaginas(Integer qtd) {
        livro.setQuantidadePaginas(qtd);
    }

    public void comDataFinalizacao(String data) throws ParseException {
        livro.setDataFinalizacao(data);
    }

    public Livro finalizar() {
        return this.livro;
    }
}
