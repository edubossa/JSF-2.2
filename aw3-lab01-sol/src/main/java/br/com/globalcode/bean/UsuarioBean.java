package br.com.globalcode.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.globalcode.model.Usuario;

@Named
@RequestScoped
public class UsuarioBean {
	
	private Usuario usuario;
	
	public UsuarioBean() {
		super();
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String salvar(){
		
		System.out.println(this.usuario); 
		
		return "sucesso";
	}
	
}