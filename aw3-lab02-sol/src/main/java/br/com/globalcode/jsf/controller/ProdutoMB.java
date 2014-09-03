package br.com.globalcode.jsf.controller;

import br.com.globalcode.model.Categoria;
import br.com.globalcode.model.ProdutoMaterial;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("ProdutoMB")
@RequestScoped
public class ProdutoMB {

    private ProdutoMaterial produto = new ProdutoMaterial();
    
    public ProdutoMB() {
        Categoria categoria = new Categoria(new Integer(1),"DVD",true );
        produto = new ProdutoMaterial(new Integer(2), categoria,"Sony", "Aprenda Java em 21 dias",45.00,true, 2 );
    }
    public void setProduto(ProdutoMaterial produto) {
        this.produto = produto;
    }
    public ProdutoMaterial getProduto() {
        return produto;
    }
    
    public String salvar() {
    	System.out.println(produto);
    	return "sucesso";
    }
}
