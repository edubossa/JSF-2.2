package br.com.globalcode.model;

import java.util.Date;

public class PagamentoCartaoCredito extends Pagamento {
    
    private BandeiraCartaoCredito bandeiraCartaoCredito;
    private String nomeTitular;
    private String numeroCartao;
    private long numeroConfirmacao;
    private Date dataValidadeCartao;
    
    public PagamentoCartaoCredito(){
        bandeiraCartaoCredito = new BandeiraCartaoCredito();
    }
    
    public PagamentoCartaoCredito(Pagamento pagamento) {
        super(pagamento);
        if (pagamento instanceof PagamentoCartaoCredito){
            setNomeTitular(((PagamentoCartaoCredito)pagamento).getNomeTitular());
            setNumeroConfirmacao(((PagamentoCartaoCredito)pagamento).getNumeroConfirmacao());
            setNumeroCartao(((PagamentoCartaoCredito)pagamento).getNumeroCartao());
            setDataValidadeCartao(((PagamentoCartaoCredito)pagamento).getDataValidadeCartao());
            setBandeiraCartaoCredito(((PagamentoCartaoCredito)pagamento).getBandeiraCartaoCredito());
        }
    }
    
    public PagamentoCartaoCredito(Date dataPagamento, double valor, BandeiraCartaoCredito bandeiraCartaoCredito,
            String nomeTitular, String numeroCartao, long numeroConfirmacao, Date dataValidadeCartao) {
        super(dataPagamento,valor);
        this.bandeiraCartaoCredito = bandeiraCartaoCredito;
        this.nomeTitular = nomeTitular;
        this.numeroCartao = numeroCartao;
        this.numeroConfirmacao = numeroConfirmacao;
        this.dataValidadeCartao = dataValidadeCartao;
    }
    
    public PagamentoCartaoCredito(Integer id, Date dataPagamento, double valor, BandeiraCartaoCredito bandeiraCartaoCredito,
            String nomeTitular, String numeroCartao, long numeroConfirmacao, Date dataValidadeCartao) {
        this(dataPagamento, valor, bandeiraCartaoCredito, nomeTitular, numeroCartao, numeroConfirmacao, dataValidadeCartao);
        this.id = id;
    }
    
    public BandeiraCartaoCredito getBandeiraCartaoCredito(){
        return bandeiraCartaoCredito;
    }
    
    public void setBandeiraCartaoCredito(BandeiraCartaoCredito bandeiraCartaoCredito){
        this.bandeiraCartaoCredito = bandeiraCartaoCredito;
    }
    
    public String getNomeTitular(){
        return nomeTitular;
    }
    
    public void setNomeTitular(String nomeTitular){
        this.nomeTitular = nomeTitular;
    }
    
    public String getNumeroCartao(){
        return numeroCartao;
    }
    
    public void setNumeroCartao(String numeroCartao){
        this.numeroCartao = numeroCartao;
    }
    
    public long getNumeroConfirmacao(){
        return numeroConfirmacao;
    }
    
    public void setNumeroConfirmacao(long numeroConfirmacao){
        this.numeroConfirmacao = numeroConfirmacao;
    }
    
    public Date getDataValidadeCartao(){
        return dataValidadeCartao;
    }
    
    public void setDataValidadeCartao(Date dataValidadeCartao){
        this.dataValidadeCartao = dataValidadeCartao;
    }
    
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        boolean result = false;
        if (o instanceof PagamentoCartaoCredito) {
            PagamentoCartaoCredito outro = (PagamentoCartaoCredito) o;
            result = this.getNumeroCartao() != null &&
                    this.getDataPagamento() != null &&
                    this.getNumeroCartao().equals(outro.getNumeroCartao()) &&
                    this.getDataPagamento().equals(outro.getDataPagamento());
        }
        return result;
    }
    
    public int hashCode() {
        int result = getNumeroCartao() != null ? getNumeroCartao().hashCode() : 47;
        result = (getDataPagamento() != null ? getDataPagamento().hashCode() : 41) + 13 * result;
        return result;
    }
    
    
}
