package br.com.alura.agenda.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Locale;

import br.com.alura.agenda.R;
import br.com.alura.agenda.model.entities.Livro;
import br.com.alura.agenda.model.entities.LivroFactory;
import br.com.alura.agenda.usecases.dao.LivroDAO;

public class FormularioLivroActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Cadastro de Livro";
    private final LivroDAO dao = new LivroDAO();
    private final LivroFactory livroFactory = new LivroFactory();

    private Button botaoSalvar;
    private EditText campoNome;
    private EditText campoAutor;
    private EditText campoQuantidade;
    private EditText campoData;

    @NonNull
    private Livro criaLivro() throws ParseException {
        Livro livroCriado;

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
    }
    ;

    private boolean validar() {

        if (campoNome.getText().toString().isEmpty() || campoAutor.getText().toString().isEmpty() || campoData.getText().toString().isEmpty()) {
            Toast.makeText(FormularioLivroActivity.this,
                    "Por favor insira todos os dados.", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Integer quantidade = Integer.parseInt(campoQuantidade.getText().toString());
            if (quantidade == 0) {
                Toast.makeText(FormularioLivroActivity.this,
                        "Por favor inserir um número maior do que 0 na quantidade de páginas.", Toast.LENGTH_SHORT).show();
                return false;
            }
            ;
        } catch (NumberFormatException e) {
            Toast.makeText(FormularioLivroActivity.this,
                    "Por favor inserir um número na quantidade de páginas.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    ;

    private void inicializar() {
        campoNome = findViewById(R.id.activities_formulario_livro_nome_livro);
        campoAutor = findViewById(R.id.activities_formulario_livro_autor);
        campoQuantidade = findViewById(R.id.activities_formulario_livro_quantidade_paginas);
        campoData = findViewById(R.id.activities_formulario_livro_data_finalizado);
    }

    private Livro verificaDadosSerializados() {
        Intent dados = getIntent();
        Livro livro = null;

        if (dados.hasExtra("livro")) {
            livro = (Livro) dados.getExtras().getParcelable("livro");
            campoNome.setText(livro.getNome());
            campoQuantidade.setText(String.format(Locale.US, "%d", livro.getQuantidadePaginas()));
            campoData.setText(livro.getDataFormatada());
            campoAutor.setText(livro.getAutor());
        }

        return livro;
    }

//    private void configuraBotaoSalvar() {
//        botaoSalvar = findViewById(R.id.activities_formulario_livro_button_salvar);
//        Livro livroSerializado = verificaDadosSerializados();
//        botaoSalvar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (validar()) {
//                    try {
//                        Livro livroInputCampos = criaLivro();
//                        if (livroSerializado == null) {
//                            dao.salvar(livroInputCampos);
//                        } else {
//                            livroInputCampos.setId(livroSerializado.getId());
//                            dao.editar(livroInputCampos);
//                        }
//                        finish();
//                    } catch (ParseException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        });
//    }

    private void salvarFormulario() throws ParseException {
        Livro livroSerializado = verificaDadosSerializados();

        if(validar()) {
            Livro livroInputCampos = criaLivro();
            if(livroSerializado == null) {
                dao.salvar(livroInputCampos);
                Toast.makeText(this, livroInputCampos.getNome() + " foi salvo na sua biblioteca.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                livroInputCampos.setId(livroSerializado.getId());
                dao.editar(livroInputCampos);
                finish();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activities_formulario_livro);
        setTitle(TITLE_APPBAR);
        inicializar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activities_formulario_livro_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activities_lista_livros_menu_salvar) {
            try {
                System.out.println("Clicou aqui.");
                salvarFormulario();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
