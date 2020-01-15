package br.com.dwprinter.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.dwprinter.classe.Produto;
import br.com.dwprinter.dao.DAOGenerico;


public class DAOGenericoHibernate<E> implements DAOGenerico<E>, Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	protected EntityManager manager;
	private Class classeEntidade;
	

	public DAOGenericoHibernate(Class classeEntidade) {
		this.classeEntidade = classeEntidade;
	}

	public List<Produto> consultaproduto(String palavra) {
		List<Produto> list = new ArrayList<>();

		javax.persistence.Query query = (javax.persistence.Query) manager.createNativeQuery(
						" SELECT " 
						+ " p.produtoid, "
						+ " p.nome_produto, "
						+ " p.referencia_produto, "
						+ " i.IMAGEM_PRODUTO "
						+ " from produto p "
						+ " left join PRODUTO_IMAGEM i on i.produtoid = p.produtoid "
						+ " where p.STATUS_PRODUTO = 'ATIVO' "
						+ " and (p.nome_produto like '%"+palavra+"%' or p.referencia_produto like '%"+palavra+"%') "
						+ " order by p.referencia_produto ");

		List<Object[]> lista = query.getResultList();

		for (Object[] row : lista) {
			Produto produto = new Produto();

			produto.setCodigoproduto((BigDecimal) row[0]);
			produto.setNomeproduto((String) row[1]);
			produto.setReferencia((String) row[2]);
			produto.setImagem((Blob) row[3] );
			
			list.add(produto);
		}
		return list;
}
	

	/* pegar usuario conectado */
	public String usuarioconectado() {
		String nome;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			nome = ((UserDetails) principal).getUsername();
		} else {
			nome = principal.toString();
		}
		// System.out.println(nome);
		return nome;
	}

}
