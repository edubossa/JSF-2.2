package br.com.globalcode.jsf.controller;

import br.com.globalcode.dao.DAOException;
import br.com.globalcode.dao.jdbc.CategoriaJDBC;
import br.com.globalcode.model.Categoria;
import br.com.globalcode.model.ProdutoMaterial;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("ProdutoMB")
@RequestScoped
public class ProdutoMB {

    private ProdutoMaterial produto = new ProdutoMaterial();
    private List<Categoria> categoriasDisponiveis;
    private Integer categoriaSelecionada;

    public ProdutoMB() {
        categoriasDisponiveis = new ArrayList<Categoria>();
        produto = new ProdutoMaterial();
    }

    public void setProduto(ProdutoMaterial produto) {
        this.produto = produto;
    }

    public ProdutoMaterial getProduto() {
        return produto;
    }

    public List<Categoria> getCategoriasDisponiveis() {
        if (categoriasDisponiveis.isEmpty()) {
            try {
                List<Categoria> categoriasList = CategoriaJDBC.getInstance().getAll();
                for (Categoria cat : categoriasList) {
                    categoriasDisponiveis.add(cat);
                }
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
        return categoriasDisponiveis;
    }

    public void setCategoriasDisponiveis(List<Categoria> categoriasDisponiveis) {
        this.categoriasDisponiveis = categoriasDisponiveis;
    }

    public Integer getCategoriaSelecionada() {
        return categoriaSelecionada;
    }

    public void setCategoriaSelecionada(Integer categoriaSelecionada) {
        System.out.println("[ProdutoMB] Categoria Selecionada = " + categoriaSelecionada);
        this.categoriaSelecionada = categoriaSelecionada;
        try {
            Categoria c = CategoriaJDBC.getInstance().getByPrimaryKey(categoriaSelecionada);
            System.out.println("categoria = " + c);
            produto.setCategoria(c);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    
    public String salvar() {
        System.out.println("[ProdutoMB - salvar] " + produto.getNome() +
                " categoria do produto = " + produto.getCategoria());
        return null;
    }

}
