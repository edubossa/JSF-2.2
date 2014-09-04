package br.com.globalcode.jsf.controller;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("calculadoraMB")
@RequestScoped
public class CalculadoraManagedBean implements Serializable {

    private int numero1;
    private int numero2;
    private int resultado;

    public CalculadoraManagedBean() {
        super();
    }

    public int getNumero1() {
        return numero1;
    }

    public void setNumero1(int numero1) {
        this.numero1 = numero1;
    }

    public int getNumero2() {
        return numero2;
    }

    public void setNumero2(int numero2) {
        this.numero2 = numero2;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

    public void multiplica() {
        resultado = numero1 * numero2;
    }
    
    public void soma() {
        resultado = numero1 + numero2;
    }

    public void divisao() {
        resultado = numero1 / numero2;
    }
}
