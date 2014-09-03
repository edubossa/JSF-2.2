package br.com.globalcode.model;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import br.com.globalcode.comparator.ComparatorProdutoCategoriaNome;

public class CatalogoProdutos implements Serializable{
    
    private Integer id;  
    private String nome;
    private Date validade;
    private SortedSet produtos = new TreeSet(new ComparatorProdutoCategoriaNome());
    
    public CatalogoProdutos(){
    }
    
    public CatalogoProdutos(String nome, Date validade) {
      setNome(nome);
      setValidade(validade);
    }
    
    public CatalogoProdutos(Integer id, String nome, Date validade) {
      this(nome,validade);
      setId(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }    
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public Date getValidade(){
        return validade;
    }
    
    public void setValidade(Date validade){
        this.validade = validade;
    }
    
    public SortedSet getProdutos() {
        return produtos;
    }
    
    public void setProdutos(SortedSet produtos) {
        this.produtos = produtos;
    }
    
    public String toString() {
        return getNome();
    }
    
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        boolean result = false;
        if (o instanceof Categoria) {
            CatalogoProdutos outro = (CatalogoProdutos) o;
            result = this.getNome() != null && 
                     this.getNome().equals(outro.getNome());
        }
        return result;      
    }
    
    public int hashCode() {
        int result = getNome() != null ? getNome().hashCode() : 41;
        return result;
    }    
}