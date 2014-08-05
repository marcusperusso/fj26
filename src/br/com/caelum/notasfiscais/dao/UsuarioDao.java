package br.com.caelum.notasfiscais.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.caelum.notasfiscais.modelo.Produto;
import br.com.caelum.notasfiscais.modelo.Usuario;
import br.com.caelum.notasfiscais.util.JPAUtil;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public boolean existe(Usuario usuario) {
		
		Query query = manager.createQuery("select u from Usuario u where u.login = :pLogin and u.senha = :pSenha")
						.setParameter("pLogin", usuario.getLogin())
						.setParameter("pSenha", usuario.getSenha());

		boolean encontrado = !query.getResultList().isEmpty();

		return encontrado;
	}
	
	public void cadastra(Usuario usuario) {
		manager.getTransaction().begin();
		//persiste o objeto
		manager.persist(usuario);
		manager.getTransaction().commit();
	}
	
	public List<Usuario> listaTodos() {
		 CriteriaBuilder cb = manager.getCriteriaBuilder();
		 
		  CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
		  Root<Usuario> c = query.from(Usuario.class);
		  query.select(c);
		  query.orderBy(cb.asc(c.get("login")));
		  List<Usuario> lista = manager.createQuery(query).getResultList();
		return lista; 
	}
}