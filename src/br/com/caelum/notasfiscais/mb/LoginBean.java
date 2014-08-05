package br.com.caelum.notasfiscais.mb;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.notasfiscais.dao.UsuarioDao;
import br.com.caelum.notasfiscais.modelo.Usuario;

@Named
@RequestScoped
public class LoginBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioDao usuarioDao;
	private Usuario usuario = new Usuario();
	@Inject
	private UsuarioLogadoBean usuarioLogado;
	
	public String logout() {
		this.usuario = new Usuario();
		return "produto?faces-redirect=true";
	}
	
	public String efetuaLogin(){
		boolean loginValido =  usuarioDao.existe(this.usuario);
		System.out.println("O login era valido? " + loginValido);
		if(loginValido){
			usuarioLogado.logar(usuario);
			return "produto?faces-redirect=true";
		} else {
			usuarioLogado.deslogar();
			this.usuario = new Usuario();
			return "login";
		}
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
}
