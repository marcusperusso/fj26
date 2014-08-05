package br.com.caelum.notasfiscais.mb;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.com.caelum.notasfiscais.dao.ProdutoDao;
import br.com.caelum.notasfiscais.modelo.Produto;

@javax.faces.bean.ManagedBean
public class ProdutoBean {

	@Inject
	private ProdutoDao produtoDao;
	
	private Produto produto = new Produto();
	private String nomeProduto = new String();
	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	private List<Produto> produtos;
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void cancelarEdicao() {
		setProduto(new Produto());
	}

	public void remove(Produto produto) {
		try {
			produtoDao.remove(produto);
			this.produtos = produtoDao.listaTodos();
		}catch(Exception e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage( null, new FacesMessage( "Produto " + produto.getNome() + " já vinculado. Não é possível excluí-lo."));
		}
	}
	
	public String grava() {
		System.out.println("passou no grava");
		if(produto.getId() == null){
			produtoDao.adiciona(produto);
		}else {
			produtoDao.atualiza(produto);
		}
		this.produto = new Produto();
		this.produtos = produtoDao.listaTodos();
		return "produto?faces-redirect=true";
	}
	
	public String redirecionaCadastroUsuario(){
		return "usuario?faces-redirect=true";
	}
	
	public String redirecionaCadastroNotasFiscais() {
		return "notaFiscal?faces-redirec=true";
	}
	
	public void consulta(){
		this.produtos = produtoDao.buscaPorNome(getNomeProduto());
	}
	
	public void limpar(){
		setNomeProduto("");
		this.produto = new Produto();
		this.produtos = produtoDao.listaTodos();
	}
	
	public Float getTotal(){
		Float total = 0f;
		for (Produto produto : produtos) {
			if(produto.getPreco() != null) {
				total += produto.getPreco().floatValue();
			}
		}
		return total;
	}
	public List<Produto> getProdutos(){
		if(produtos == null){
			System.out.println("Carregando produtos...");
			produtos = produtoDao.listaTodos();
		}
		return produtos;
	}
}
