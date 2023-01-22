package br.com.alura.agenda.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;

import br.com.alura.agenda.R;
import br.com.alura.agenda.model.entities.Livro;
import br.com.alura.agenda.model.entities.LivroFactory;
import br.com.alura.agenda.usecases.dao.LivroDAO;

public class FormularioLivroActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Cadastro de Livro";
    private final LivroDAO dao = new LivroDAO();
    private final LivroFactory livroFactory = new LivroFactory();

    private EditText campoNome;
    private EditText campoAutor;
    private EditText campoQuantidade;
    private EditText campoData;

    @NonNull
    private Livro criaLivro() throws ParseException {
        Livro livroCriado = new Livro();

        String nome = campoNome.getText().toString();
        String autor = campoAutor.getText().toString();
        String data = campoData.getText().toString();
        Integer quantidade = Integer.parseInt(campoQuantidade.getText().toString());

        livroFactory.comNome(nome);
        livroFactory.comAutor(autor);
        livroFactory.comPaginas(quantidade);
        livroFactory.comDataFinalizacao(data);
        livroCriado = livroFactory.finalizar();

        return livroCriado;
    };

    private boolean validar() {

        if(campoNome.getText().toString().isEmpty() || campoAutor.getText().toString().isEmpty() || campoData.getText().toString().isEmpty()) {
            Toast.makeText(FormularioLivroActivity.this,
                    "Por favor insira todos os dados.", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Integer quantidade = Integer.parseInt(campoQuantidade.getText().toString());
            if(quantidade == 0) {
                Toast.makeText(FormularioLivroActivity.this,
                        "Por favor inserir um número maior do que 0 na quantidade de páginas.", Toast.LENGTH_SHORT).show();
                return false;
            };
        } catch(NumberFormatException e) {
            Toast.makeText(FormularioLivroActivity.this,
                    "Por favor inserir um número na quantidade de páginas.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    };

    private void inicializar() {
        campoNome = findViewById(R.id.activities_formulario_livro_nome_livro);
        campoAutor = findViewById(R.id.activities_formulario_livro_autor);
        campoQuantidade = findViewById(R.id.activities_formulario_livro_quantidade_paginas);
        campoData = findViewById(R.id.activities_formulario_livro_data_finalizado);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_formulario_livro);
        setTitle(TITLE_APPBAR);

        inicializar();
        Button botaoSalvar = findViewById(R.id.activities_formulario_livro_button_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validar()) {
                    Livro livroCriado;
                    try {
                        livroCriado = criaLivro();
                        dao.salvar(livroCriado);
                        finish();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Intent dados = getIntent();
        try {
            Livro livro = (Livro) dados.getExtras().getParcelable("livro");
            campoNome.setText(livro.getNome());
            campoQuantidade.setText(String.valueOf(livro.getQuantidadePaginas()));
            campoData.setText(livro.getDataFormatada());
            campoAutor.setText(livro.getAutor());
        } catch (NullPointerException e) {
            System.out.println("No intent");
        }

    }
}
