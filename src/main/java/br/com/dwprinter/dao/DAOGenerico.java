package br.com.dwprinter.dao;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.dwprinter.classe.Produto;

public interface DAOGenerico<E> {
	
	public List<Produto> consultaproduto(String produto);

}
