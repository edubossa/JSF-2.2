package br.com.globalcode.model;

import java.io.Serializable;

public class ItemCompra implements Serializable{
    
    private Integer id;
    private int quantidade;
    private int desconto;
    private Produto produto;
    
    public ItemCompra(){
    }
    
    public ItemCompra(Produto produto, int quantidade, int desconto) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.desconto = desconto;
    }
    
    public ItemCompra(Integer id, Produto produto, int quantidade, int desconto) {
        this(produto, quantidade, desconto);
        this.id = id;
    }
    
    public Integer getId(){
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public int getQuantidade(){
        return quantidade;
    }
    
    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }
    
    public int getDesconto(){
        return desconto;
    }
    
    public void setDesconto(int desconto){
        this.desconto = desconto;
    }
    
    public Produto getProduto(){
        return produto;
    }
    
    public void setProduto(Produto produto){
        this.produto = produto;
    }
    
    public String toString() {
        return getId() != null ? getId().toString() : null;
    }
    
    public boolean equals(Object o) {
    	if (o == null) return false;
      	if (this == o) return true;      
        boolean result = false;
        if (o instanceof ItemCompra){
            ItemCompra outro = (ItemCompra) o;
            result = this.getQuantidade() == outro.getQuantidade() && 
                     this.getProduto() != null && 
                     this.getProduto().equals(outro.getProduto());
        }
        return result;
    }
    
    public int hashCode() {
        int result = getQuantidade(); 
        result = (getProduto() != null ? getProduto().hashCode() : 41) + 23 * result; 
        return result;
    }
    
    
}