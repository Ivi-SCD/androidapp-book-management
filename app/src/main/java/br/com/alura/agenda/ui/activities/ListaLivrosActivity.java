package br.com.alura.agenda.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
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
    private static final String TITLE_APPBAR = "Biblioteca";
    private List <Livro> listaLivros;
    private ArrayAdapter<Livro> adapter;

    private void configuraCliqueNoItem() {
        listaLivros = dao.selecionar();
        ListView listaView = findViewById(R.id.activities_lista_livros_lista_de_livros);
        listaView.setAdapter(adapter);
        registerForContextMenu(listaView);
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

    private void configuraFloatingButton() {
        FloatingActionButton fabAdicionarNovoLivro = findViewById(R.id.activities_lista_livros_fab_novo_livro);
        fabAdicionarNovoLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaLivrosActivity.this, FormularioLivroActivity.class));
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(TITLE_APPBAR);
        setContentView(R.layout.activities_lista_livros);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dao.selecionar());

        adapter.addAll(dao.selecionar());
        configuraFloatingButton();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activities_lista_livros_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {


        CharSequence tituloMenu = item.getTitle();
        int itemId = item.getItemId();

        if(itemId == R.id.activities_lista_livros_menu_remover) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Livro livroEscolhido = adapter.getItem(menuInfo.position);
            adapter.remove(livroEscolhido);
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.addAll(dao.selecionar());
        configuraCliqueNoItem();

    }
}
