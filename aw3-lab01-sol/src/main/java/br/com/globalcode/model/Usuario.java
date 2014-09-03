package br.com.globalcode.model;

import java.io.Serializable;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 131482989237072692L;

	private String nome;
	private String email;
	private String senha;
	private char sexo;
	private boolean casado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public boolean isCasado() {
		return casado;
	}

	public void setCasado(boolean casado) {
		this.casado = casado;
	}

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", email=" + email + ", senha="
				+ senha + ", sexo=" + sexo + ", casado=" + casado + "]";
	}

}