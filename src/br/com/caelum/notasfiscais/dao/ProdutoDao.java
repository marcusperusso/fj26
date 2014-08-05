package br.com.caelum.notasfiscais.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.caelum.notasfiscais.modelo.Produto;

public class ProdutoDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public void adiciona(Produto produto) {
		manager.getTransaction().begin();
		//persiste o objeto
		manager.persist(produto);
		manager.getTransaction().commit();
	}


	public void remove(Produto produto) {
		manager.getTransaction().begin();
		manager.remove(manager.merge(produto));
		manager.getTransaction().commit();
	}

	public void atualiza(Produto produto) {
		manager.getTransaction().begin();

		manager.merge(produto);
		
		manager.getTransaction().commit();
	}

	public List<Produto> buscaPorNome(String nome) {
		String jpql = "select p from Produto p where "
				+ " lower(p.nome) like :nome order by p.nome";

		List<Produto> lista = manager.createQuery(jpql, Produto.class)
				.setParameter("nome", nome + "%").getResultList();
		return lista; 
	}

	public List<Produto> listaTodos() {
		 CriteriaBuilder cb = manager.getCriteriaBuilder();
		 
		  CriteriaQuery<Produto> query = cb.createQuery(Produto.class);
		  Root<Produto> c = query.from(Produto.class);
		  query.select(c);
		  query.orderBy(cb.asc(c.get("nome")));
		  List<Produto> lista = manager.createQuery(query).getResultList();
		return lista; 
	}
	
	public Produto buscaPorId(Long id) {
		return manager.find(Produto.class, id);
	}
}