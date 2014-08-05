package br.com.caelum.notasfiscais.mb;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import br.com.caelum.notasfiscais.dao.UsuarioDao;
import br.com.caelum.notasfiscais.modelo.Usuario;

@ManagedBean
@RequestScoped
public class UsuarioBean {

	@Inject
	private UsuarioDao usuarioDao;
	
	private Usuario usuarioNovo = new Usuario();
	private List<Usuario> lista;
	
	public void cadastra() {
		usuarioDao.cadastra(usuarioNovo);
		this.usuarioNovo = new Usuario();
		this.lista = usuarioDao.listaTodos();
	}

	public Usuario getUsuarioNovo() {
		return usuarioNovo;
	}

	public void setUsuarioNovo(Usuario usuarioNovo) {
		this.usuarioNovo = usuarioNovo;
	}
	
	public String redirecionaParaProdutos(){
		return "produto?faces-redirect=true";
	}

	public List<Usuario> getLista() {
		if(lista == null){
			lista = new ArrayList<Usuario>();
			lista.addAll(usuarioDao.listaTodos());
		}
		return lista;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}
}
