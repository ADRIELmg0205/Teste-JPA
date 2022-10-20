package br.edu.unoesc.testejpa.app;

import java.math.BigDecimal;
import java.util.List;

import br.edu.unoesc.testejpa.model.Funcionario;
import br.edu.unoesc.testejpa.model.Projeto;
import br.edu.unoesc.testejpa.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class App8 {

	public static EntityManager em;

	public static void main(String[] args) {
		adicionarDados();

		listarPorProjeto();

		listarPorFuncionario();
	}

	public static void adicionarDados() {
		em = JPAUtil.getEntityManager();

		Projeto p1 = new Projeto("Projeto P1");
		Projeto p2 = new Projeto("Projeto P2");

		Funcionario f1 = new Funcionario("Ana", java.sql.Date.valueOf("2000-01-01"), new BigDecimal("1000.00"));
		Funcionario f2 = new Funcionario("Bernardo", java.sql.Date.valueOf("2000-04-01"), new BigDecimal("2000.00"));
		Funcionario f3 = new Funcionario("Carlos", java.sql.Date.valueOf("2000-01-01"), new BigDecimal("3000.00"));

		// adiciona Ana e Bernardo ao Projeto P1
		p1.adicionarFuncionario(f1);
		p1.adicionarFuncionario(f2);

		// adiciona o Projeto P2 aos funcionários Ana e Carlos
		f1.adicionarProjeto(p2);
		f3.adicionarProjeto(p2);

		em.getTransaction().begin();

		em.persist(p1);
		em.persist(p2);
		em.persist(f1);
		em.persist(f2);
		em.persist(f3);

		em.getTransaction().commit();

		em.close();
	}

public static void listarPorProjeto() {
		em = JPAUtil.getEntityManager();
		String jpql = "SELECT p FROM Projeto p";
		List<Projeto> projetos = em.createQuery(jpql, Projeto.class).getResultList();
		System.out.println("================================");
		System.out.println("Projetos e seus funcionários");
		System.out.println("================================");
		for (Projeto p : projetos) {
			System.out.println(p.getId() + " - " + p.getNome());
			for (Funcionario f : p.getFuncionarios())
				System.out.println("\t" + f.getId() + " - " + f.getNome());
		}
	}

	public static void listarPorFuncionario() {
		em = JPAUtil.getEntityManager();
		String jpql = "SELECT f FROM Funcionario f";
		List<Funcionario> funcionarios = em.createQuery(jpql, Funcionario.class).getResultList();
		System.out.println("================================");
		System.out.println("Funcionários e seus projetos");
		System.out.println("================================");
		for (Funcionario f : funcionarios) {
			System.out.println(f.getId() + " - " + f.getNome());
			for (Projeto p : f.getProjetos()) {
				System.out.println("\t" + p.getId() + " - " + p.getNome());
			}
		}
	}
}
