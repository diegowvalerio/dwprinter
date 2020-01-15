package br.com.dwprinter.servico;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.dwprinter.classe.Produto;
import br.com.dwprinter.dao.DAOProduto;
@Dependent
public class ServicoProduto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DAOProduto dao;
	
	public List<Produto> produtos(String palavra){
		List<Produto> pro = null;
		if(!palavra.equals("")){
			pro = dao.consultaproduto(palavra);
		}
		return pro;
	}
	
}
