package br.com.alura.agenda.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Livro implements Parcelable {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private Integer id;
    private String nome;
    private String autor;
    private Integer quantidadePaginas;
    private Date dataFinalizacao;

    public Livro(){}

    public Livro(String nome, String autor, Integer quantidadePaginas, String dataFinalizacao) throws ParseException {
        this.nome = nome;
        this.autor = autor;
        this.quantidadePaginas = quantidadePaginas;
        setDataFinalizacao(dataFinalizacao);
    }

    protected Livro(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        autor = in.readString();
        quantidadePaginas = in.readInt();
        dataFinalizacao = new Date(in.readLong());
    }

    public static final Creator<Livro> CREATOR = new Creator<Livro>() {
        @Override
        public Livro createFromParcel(Parcel in) {
            return new Livro(in);
        }

        @Override
        public Livro[] newArray(int size) {
            return new Livro[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getQuantidadePaginas() {
        return quantidadePaginas;
    }

    public void setQuantidadePaginas(Integer quantidadePaginas) {
        this.quantidadePaginas = quantidadePaginas;
    }

    public Date getDataFinalizacao() {
        return dataFinalizacao;
    }

    public String getDataFormatada() {
        return sdf.format(this.dataFinalizacao);
    }

    public void setDataFinalizacao(Date dataFinalizacao) {
        this.dataFinalizacao = dataFinalizacao;
    }

    public void setDataFinalizacao(String data) throws ParseException {
        this.dataFinalizacao = sdf.parse(data);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return nome + " ("+ autor + ")" +"\nQuantidade de Páginas: " + quantidadePaginas + "\n" + sdf.format(dataFinalizacao);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(nome);
        parcel.writeString(autor);
        parcel.writeInt(quantidadePaginas);
        parcel.writeLong(dataFinalizacao.getTime());
    }
}
