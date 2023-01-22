package br.com.alura.agenda.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.model.entities.Livro;
import br.com.alura.agenda.usecases.dao.LivroDAO;

public class ListaLivrosActivity extends AppCompatActivity {
    private final LivroDAO dao = new LivroDAO();
    public static final String TITLE_APPBAR = "Biblioteca";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(TITLE_APPBAR);
        setContentView(R.layout.activity_lista_livros);

        FloatingActionButton fabAdicionarNovoLivro = findViewById(R.id.activities_lista_livros_fab_novo_livro);

        try {
            dao.salvar(new Livro("Frankestein", "Mary Shelley", 294, "20/01/2023"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        fabAdicionarNovoLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaLivrosActivity.this, FormularioLivroActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        ListView listaView = findViewById(R.id.activities_lista_livros_lista_de_livros);
        listaView.setAdapter(new ArrayAdapter<Livro>(this, android.R.layout.simple_list_item_1, dao.selecionar()));
        List <Livro> listaLivros = dao.selecionar();

        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Livro livroSelecionado = listaLivros.get(i);

                Intent irParaFormulario = new Intent(new Intent(ListaLivrosActivity.this, FormularioLivroActivity.class));

                irParaFormulario.putExtra("livro", livroSelecionado);

                startActivity(irParaFormulario);
            }
        });


    }
}
