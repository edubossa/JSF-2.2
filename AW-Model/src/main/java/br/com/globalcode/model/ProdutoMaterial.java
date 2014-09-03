package br.com.globalcode.model;

public class ProdutoMaterial extends Produto {

    private double taxaEntrega;

    public ProdutoMaterial() {
    }

    public ProdutoMaterial(Produto produto) {
        super(produto);
        if (produto instanceof ProdutoMaterial) {
            setTaxaEntrega(((ProdutoMaterial)produto).getTaxaEntrega());
        }
    }

    public ProdutoMaterial(Categoria categoria, String marca, String nome,
                           double preco, boolean ativo, double taxaEntrega) {
        super(categoria, marca, nome, preco, ativo);
        this.taxaEntrega = taxaEntrega;
    }

    public ProdutoMaterial(Integer id, Categoria categoria, String marca,
                           String nome, double preco, boolean ativo,
                           double taxaEntrega) {
        this(categoria, marca, nome, preco, ativo, taxaEntrega);
        this.id = id;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

}
