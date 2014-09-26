package br.com.globalcode.jsf.controller;

import br.com.globalcode.aw.dao.ProdutoDAO;
import br.com.globalcode.aw.dao.ProdutoDB;
import br.com.globalcode.exception.GlobalcodeException;
import br.com.globalcode.model.CarrinhoCompras;
import br.com.globalcode.model.Cliente;
import br.com.globalcode.model.Item;
import br.com.globalcode.model.Produto;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;

@Named(value = "comprasMB")
@FlowScoped("compras")
public class ComprasMB implements Serializable {

    private CarrinhoCompras carrinhoCompras;
    private Cliente cliente;
    private String bandeiraCartao;
    private String numeroCartao;
    private String validadeCartao;

    public ComprasMB() {
        cliente = new Cliente();
    }    
    
    public List<Produto> getCatalogo() {
        ProdutoDAO produtosDB = new ProdutoDB();
        List listaProdutos = null;
        try {
            listaProdutos = produtosDB.getCatalogoProdutos();
        } catch (GlobalcodeException e) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage("Erro ao carregar o catalogo de produtos"));
            e.printStackTrace();
        }
        return listaProdutos;
    }

    public String adicionarItem(Produto produto) {
        Item item = new Item(produto, 1);
        if (carrinhoCompras == null) {
            carrinhoCompras = new CarrinhoCompras(item);
        } else {
            carrinhoCompras.addItem(item);
        }
        return "CarrinhoComprasView";
    }

    public CarrinhoCompras getCarrinhoCompras() {
        return carrinhoCompras;
    }

    public void setCarrinhoCompras(CarrinhoCompras carrinhoCompras) {
        this.carrinhoCompras = carrinhoCompras;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(String bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getValidadeCartao() {
        return validadeCartao;
    }

    public void setValidadeCartao(String validadeCartao) {
        this.validadeCartao = validadeCartao;
    }
    
    
}
