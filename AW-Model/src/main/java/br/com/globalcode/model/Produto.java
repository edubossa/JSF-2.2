package br.com.globalcode.model;

import java.io.Serializable;

public abstract class Produto implements Serializable {

    protected Integer id;
    protected String nome;
    protected double preco;
    protected String marca;
    protected Categoria categoria;
    protected boolean ativo = true;

    public Produto() {
        categoria = new Categoria();
    }

    public Produto(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        this.marca = produto.getMarca();
        this.categoria = produto.getCategoria();
        this.ativo = produto.isAtivo();
    }

    public Produto(Categoria categoria, String marca, String nome,
                   double preco, boolean ativo) {
        this.categoria = categoria;
        this.marca = marca;
        this.nome = nome;
        this.preco = preco;
        this.ativo = ativo;
    }

    public Produto(Integer id, Categoria categoria, String marca, String nome,
                   double preco, boolean ativo) {
        this(categoria, marca, nome, preco, ativo);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String toString() {
        return getNome() + " - " + getId();
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this == o)
            return true;
        boolean result = false;
        if (o instanceof Produto) {
            Produto outro = (Produto)o;
            result =
                this.getNome() != null && this.getMarca() != null && this.getNome()
                .equals(outro.getNome()) &&
                this.getMarca().equals(outro.getMarca());
        }
        return result;
    }

    public int hashCode() {
        int result = getNome() != null ? getNome().hashCode() : 41;
        result =
            (getMarca() != null ? getMarca().hashCode() : 37) + 17 * result;
        return result;
    }

}
