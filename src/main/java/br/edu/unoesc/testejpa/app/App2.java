package br.edu.unoesc.testejpa.app;

import java.math.BigDecimal;
import java.util.List;

import br.edu.unoesc.testejpa.model.Pessoa;
import br.edu.unoesc.testejpa.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class App2 {

	private static EntityManager em;

	public static void main(String[] args) {
		App2 app = new App2();
		em = JPAUtil.getEntityManager();
                               //Adicionar pessoas
		app.adiciona();
                               // Alterar dados
		app.altera();

		//Dados da pessoa 1
		System.out.println("Dados da pessoa 1");
		System.out.println(app.buscarPorId(1).getNome());
		
		//Dados da pessoa 10
		System.out.println("Dados da pessoa 10");
		Pessoa p = app.buscarPorId(10);
		if(p==null) {
			System.out.println("Pessoa inesistente!");
		}else {
			System.out.println(p.getNome());
		}
		
		//Todos os dados cadastrados		
		System.out.println("Todos os cadastrados");
		List<Pessoa>lista = app.buscarTodos();
		for(Pessoa pessoa: lista) {
			System.out.println(pessoa.getNome());
		}
		//ou
		//app.buscarTodos().forEach(System.out::println);
				
		//Buscar por nome
		System.out.println("Buscar por nome Maria");
		lista = app.buscarPorNome("Maria");
		for(Pessoa pessoa: lista) {
			System.out.println(pessoa.getNome());
		}
		
		//Excluir 
		app.exclui();
		
		em.close();
		JPAUtil.closeEntityManagerFactory();
	}

	
	private void adiciona() {
		Pessoa p1 = new Pessoa("Otilia", java.sql.Date.valueOf("2000-04-01"), new BigDecimal("10000.0"));
		Pessoa p2 = new Pessoa("Maria", java.sql.Date.valueOf("2001-05-02"), new BigDecimal("20000.0"));

		// inicia a transação, persiste os objetos e faz commit
		em.getTransaction().begin();
		em.persist(p1);
		em.persist(p2);
		em.getTransaction().commit();
	}

private List<Pessoa> buscarPorNome(String nome) {
	String jpql = "SELECT p FROM Pessoa p WHERE p.nome LIKE: nome";
	return em.createQuery(jpql, Pessoa.class).setParameter("nome", "%" + nome + "%").getResultList();
}

private List<Pessoa> buscarTodos() {
	String jpql = "SELECT p FROM Pessoa p";
	return em.createQuery(jpql, Pessoa.class).getResultList();
}

private Pessoa buscarPorId(Integer id) {
	return em.find(Pessoa.class, id);
}

private void altera() {
	Pessoa pessoa = this.buscarPorId(1);
	
	pessoa.setNome("Outra Otilia");
	pessoa.setDataNascimento(java.sql.Date.valueOf("2002-05-05"));
	pessoa.setSalario(new BigDecimal("12345.67"));
	
	em.getTransaction().begin();
	em.getTransaction().commit();
}
private void exclui() {
	Pessoa pessoa = this.buscarPorId(2);
	em.getTransaction().begin();
	em.remove(pessoa);
	em.getTransaction().commit();
}

}
