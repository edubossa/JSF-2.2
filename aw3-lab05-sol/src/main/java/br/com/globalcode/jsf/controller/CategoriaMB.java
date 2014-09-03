package br.com.globalcode.jsf.controller;

import br.com.globalcode.dao.DAOException;
import br.com.globalcode.dao.jdbc.CategoriaJDBC;
import br.com.globalcode.model.Categoria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("categoriaMB")
@SessionScoped
public class CategoriaMB implements Serializable {
	private static final long serialVersionUID = 7501394786215016554L;
    
	private Collection<Categoria> categorias;
    private Categoria categoria;

    public CategoriaMB() {
        categorias = new ArrayList<Categoria>();
        categoria = new Categoria();
    }

    public void setCategorias(Collection<Categoria> categorias) {
        this.categorias = categorias;
    }

    @SuppressWarnings("unchecked")
	public Collection<Categoria> getCategorias() {
        try {
            categorias = CategoriaJDBC.getInstance().getAll();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return categorias;
    }

    public void setCategoria(Categoria categoriaSelecionada) {
        this.categoria = categoriaSelecionada;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String salvar() {
        try {
            CategoriaJDBC.getInstance().save(categoria);
            return "listaCategorias"; 
        } catch (DAOException e) {
            e.printStackTrace();
            return "paginaErro"; 
        }
    }

    public String criarNovo() {
        this.categoria = new Categoria();
        return "editaCategoria"; 
    }

    public String verDetalhe(Categoria categoria) {
        this.categoria = categoria;
        return "editaCategoria"; 
    }

}
