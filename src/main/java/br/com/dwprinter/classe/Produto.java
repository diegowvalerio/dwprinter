package br.com.dwprinter.classe;

import java.io.Serializable;
import java.lang.String;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.*;

public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private BigDecimal codigoproduto;
	private String nomeproduto;
	private String referencia;
	private Integer quantidade; 
	private Float peso;
	
	private Blob imagem;
		

	public Produto() {
		super();
	}


	public BigDecimal getCodigoproduto() {
		return codigoproduto;
	}


	public void setCodigoproduto(BigDecimal codigoproduto) {
		this.codigoproduto = codigoproduto;
	}


	public String getNomeproduto() {
		return nomeproduto;
	}


	public void setNomeproduto(String nomeproduto) {
		this.nomeproduto = nomeproduto;
	}


	public String getReferencia() {
		return referencia;
	}


	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}


	public Integer getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	public Float getPeso() {
		return peso;
	}


	public void setPeso(Float peso) {
		this.peso = peso;
	}


	public Blob getImagem() {
		return imagem;
	}


	public void setImagem(Blob imagem) {
		this.imagem = imagem;
	}

}
