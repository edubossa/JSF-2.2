package br.com.globalcode.model;

import java.util.Date;

public class PagamentoBoleto extends Pagamento {

    private String cedente;

    private String sacado;

    private String numeroCodigoBarras;

    private Date dataVencimento;

    private Banco banco;

    public PagamentoBoleto() {
        banco = new Banco();
    }

    public PagamentoBoleto(Pagamento pagamento) {
        super(pagamento);
        if (pagamento instanceof PagamentoBoleto) {
            setCedente(((PagamentoBoleto)pagamento).getCedente());
            setSacado(((PagamentoBoleto)pagamento).getSacado());
            setNumeroCodigoBarras(((PagamentoBoleto)pagamento)
                                  .getNumeroCodigoBarras());
            setDataVencimento(((PagamentoBoleto)pagamento)
                              .getDataVencimento());
            setBanco(((PagamentoBoleto)pagamento).getBanco());
        }
    }

    public PagamentoBoleto(Date dataPagamento, double valor, String cedente,
                           String sacado, String numeroCodigoBarras,
                           Date dataVencimento, Banco banco) {
        super(dataPagamento, valor);
        this.cedente = cedente;
        this.sacado = sacado;
        this.numeroCodigoBarras = numeroCodigoBarras;
        this.dataVencimento = dataVencimento;
        this.banco = banco;
    }

    public PagamentoBoleto(Integer id, Date dataPagamento, double valor,
                           String cedente, String sacado,
                           String numeroCodigoBarras, Date dataVencimento,
                           Banco banco) {
        this(dataPagamento, valor, cedente, sacado, numeroCodigoBarras,
             dataVencimento, banco);
        this.id = id;
    }


    public String getCedente() {
        return cedente;
    }

    public void setCedente(String cedente) {
        this.cedente = cedente;
    }

    public String getSacado() {
        return sacado;
    }

    public void setSacado(String sacado) {
        this.sacado = sacado;
    }

    public String getNumeroCodigoBarras() {
        return numeroCodigoBarras;
    }

    public void setNumeroCodigoBarras(String numeroCodigoBarras) {
        this.numeroCodigoBarras = numeroCodigoBarras;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this == o)
            return true;
        boolean result = false;
        if (o instanceof PagamentoBoleto) {
            PagamentoBoleto outro = (PagamentoBoleto)o;
            result =
                this.getNumeroCodigoBarras() != null && this.getDataPagamento() !=
                null &&
                this.getNumeroCodigoBarras().equals(outro.getNumeroCodigoBarras()) &&
                this.getDataPagamento().equals(outro.getDataPagamento());
        }
        return result;
    }

    public int hashCode() {
        int result =
            getNumeroCodigoBarras() != null ? getNumeroCodigoBarras().hashCode() :
                     41;
        result =
            (getDataPagamento() != null ? getDataPagamento().hashCode() : 37) +
            23 * result;
        return result;
    }


}
