package br.com.dwprinter.fabrica;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;


@ApplicationScoped
public class EntityManagerProducer implements Serializable{

	private static final long serialVersionUID = 1L;
	//conexão com seven
	@PersistenceUnit(unitName = "seven")
	private EntityManagerFactory sevenfactory;
	
	@Produces 
	@RequestScoped
	@Default
	public EntityManager createSevenEntityManager(){
		return sevenfactory.createEntityManager();
	}
	//fim
		
	public void close(@Disposes EntityManager manager){
		manager.close();
	}
	

}
