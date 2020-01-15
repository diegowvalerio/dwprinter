package br.com.dwprinter.bean;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.model.DefaultStreamedContent;

import br.com.dwprinter.classe.Produto;
import br.com.dwprinter.servico.ServicoProduto;

@ManagedBean
@SessionScoped
public class BeanProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Produto produto = new Produto();
	@Inject
	private ServicoProduto servico;
	private List<Produto> lista;

	public void filtrar(){
		lista = completaproduto(produto.getNomeproduto());
	}
	
	public List<Produto> completaproduto(String nome) {
		String n = nome.toUpperCase();
		return servico.produtos(n);
	}
	
	public DefaultStreamedContent imagem() {
		DefaultStreamedContent dsc = null;
		InputStream is = null;
		if (produto.getImagem() != null) {
			System.out.println(produto.getNomeproduto());
			try {

				is = produto.getImagem().getBinaryStream(); 								
				dsc = new DefaultStreamedContent(is, "image/jpeg");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dsc;
	}
	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public List<Produto> getLista() {
		return lista;
	}


	public void setLista(List<Produto> lista) {
		this.lista = lista;
	}


	/* RELATORIOS */	
	public void imprimeetiqueta() {
		
	}
	
}
