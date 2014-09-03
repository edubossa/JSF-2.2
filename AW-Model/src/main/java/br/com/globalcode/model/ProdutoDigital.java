package br.com.globalcode.model;

public class ProdutoDigital extends Produto {

    private String nomeArquivo;
    private int tamanho;

    public ProdutoDigital() {
    }

    public ProdutoDigital(Produto produto) {
        super(produto);
        if(produto instanceof ProdutoDigital){
            setNomeArquivo(((ProdutoDigital)produto).getNomeArquivo());
            setTamanho(((ProdutoDigital)produto).getTamanho());
        }
    }

    public ProdutoDigital(Categoria categoria, String marca, String nome,
                          double preco, boolean ativo, String nomeArquivo,
                          int tamanho) {
        super(categoria, marca, nome, preco, ativo);
        this.nomeArquivo = nomeArquivo;
        this.tamanho = tamanho;
    }

    public ProdutoDigital(Integer id, Categoria categoria, String marca,
                          String nome, double preco, boolean ativo,
                          String nomeArquivo, int tamanho) {
        this(categoria, marca, nome, preco, ativo, nomeArquivo, tamanho);
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

}
