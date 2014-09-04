package br.com.globalcode.jsf.controller;

import br.com.globalcode.dao.DAOException;
import br.com.globalcode.dao.jdbc.CategoriaJDBC;
import br.com.globalcode.dao.jdbc.ProdutoJDBC;
import br.com.globalcode.model.Categoria;
import br.com.globalcode.model.Produto;
import br.com.globalcode.model.ProdutoDigital;
import br.com.globalcode.model.ProdutoMaterial;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@Named("ProdutoMB")
@RequestScoped
public class ProdutoMB {

    private Produto produto = new ProdutoMaterial();
    private List<Categoria> categoriasDisponiveis;
    private List<Produto> produtos;
    private String tipo = "material";
    private List<String> tipos;
    private boolean produtoMaterial = true;

    public ProdutoMB() {
        categoriasDisponiveis = new ArrayList<Categoria>();
        produto = new ProdutoMaterial();
        tipos = new ArrayList<String>();
        tipos.add("material");
        tipos.add("digital");
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
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

    public List<Produto> getProdutos() {
        try {
            produtos = ProdutoJDBC.getInstance().getAll();
            return produtos;
        } catch (DAOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<String> getTipos() {
        return tipos;
    }

    public void setTipos(List<String> tipos) {
        this.tipos = tipos;
    }

    public boolean isProdutoMaterial() {
        return produtoMaterial;
    }

    public void setProdutoMaterial(boolean produtoMaterial) {
        this.produtoMaterial = produtoMaterial;
    }

    public String salvar() {
        System.out.println("Estou salvando o produto " + produto);
        try {
            ProdutoJDBC.getInstance().save(produto);
            return "listaProdutos";
        } catch (DAOException e) {
            e.printStackTrace();
            return "paginaErro";
        }
    }

    public String criarNovo() {
        produto = new ProdutoMaterial();
        return "editaProduto";
    }

    public String verDetalhe(Produto produto) {
        this.produto = produto;
        return "editaProduto";
    }

    public void valorAlterado(ValueChangeEvent valueChangeEvent) {
        // verifique se o novo valor do componente que está sendo monitorado é igual a "material",
        // através da chamada do método getNewValue() no parâmetro recebido neste método.
        String novoValor = (String) valueChangeEvent.getNewValue();
        if("material".equals(novoValor)) {
        // se for "material", mude o atributo produtoMaterial para true e
        // mude o produto para ProdutoMaterial usando a seguinte sentença:
            produtoMaterial = true;
            produto = new ProdutoMaterial(produto);
        } else { 
        // caso contrário, mude para false e
        // mude o produto para ProdutoDigital usando a seguinte sentença:
            produtoMaterial = false;
            produto = new ProdutoDigital(produto);
        }
        // por último, execute o método que renderiza a resposta...
        FacesContext.getCurrentInstance().renderResponse();
    }

}
