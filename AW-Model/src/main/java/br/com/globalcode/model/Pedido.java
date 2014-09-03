package br.com.globalcode.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pedido implements Serializable{
    
    private Long numero;
    private Date data;
    private String clienteIP;
    private String clienteBrowser;
    private String status;
    private java.util.List itensPedido;
    public Cliente cliente;
    public Pagamento pagamento;
    
    public Pedido(){
    }
    
    public Pedido(Cliente cliente, String clienteBrowser, String clienteIP, Date data, List itensPedido,
            Pagamento pagamento, String status) {
        this.cliente = cliente;
        this.clienteBrowser = clienteBrowser;
        this.clienteIP = clienteIP;
        this.data = data;
        this.itensPedido = itensPedido;
        this.pagamento = pagamento;
        this.status = status;
    }
    
    public Pedido(Long numero, Cliente cliente, String clienteBrowser, String clienteIP, Date data,
            List itensPedido, Pagamento pagamento, String status) {
        this(cliente, clienteBrowser, clienteIP, data, itensPedido, pagamento, status);
        this.numero = numero;
    }
    
    public Long getNumero(){
        return numero;
    }
    
    public void setNumero(Long numero){
        this.numero = numero;
    }
    
    public Date getData(){
        return data;
    }
    
    public void setData(Date data){
        this.data = data;
    }
    
    public String getClienteIP(){
        return clienteIP;
    }
    
    public void setClienteIP(String clienteIP){
        this.clienteIP = clienteIP;
    }
    
    public String getClienteBrowser(){
        return clienteBrowser;
    }
    
    public void setClienteBrowser(String clienteBrowser){
        this.clienteBrowser = clienteBrowser;
    }
    
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public java.util.List getItensPedido(){
        return itensPedido;
    }
    
    public void setItensPedido(java.util.List itensPedido){
        this.itensPedido = itensPedido;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Pagamento getPagamento() {
        return pagamento;
    }
    
    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
    
    public String toString() {
        return getNumero() != null ? getNumero().toString() : null;
    }
    
    public boolean equals(Object o) {
	    if (o == null) return false;
  	    if (this == o) return true;  
        boolean result = false;
        if(o instanceof Pedido) {
            Pedido outro = (Pedido) o;
            result = this.getCliente() != null &&
                     this.getData() != null &&
                     this.getCliente().equals(outro.getCliente()) &&
                     this.getData().equals(outro.getData());
        }
        return result;
    }
    
    public int hashCode() {
        int result = getCliente() != null ? getCliente().hashCode() : 41;
        result = (getData() != null ? getData().hashCode() : 37) + 23 * result;
        return result;
    }
    
    
}