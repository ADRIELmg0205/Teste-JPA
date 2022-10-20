package br.edu.unoesc.testejpa.dao;

import java.util.List;

import br.edu.unoesc.testejpa.model.Projeto;
import br.edu.unoesc.testejpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class DaoProjeto {
	private EntityManager em;

	public DaoProjeto() {
		em = JPAUtil.getEntityManager();
	}
	// métodos privados
	private DaoProjeto abrirTransacao() {
		em.getTransaction().begin();
		return this;
	}
	private DaoProjeto fecharTransacao() {
		em.getTransaction().commit();
		return this;
	}
	private DaoProjeto incluir(Projeto p) {
		em.persist(p);
		return this;
	}
	private DaoProjeto remover(Projeto p) {
		em.remove(p);
		return this;
	}
	// métodos públicos
	public DaoProjeto salvar(Projeto p) {
		return this.abrirTransacao().incluir(p).fecharTransacao();
	}
	public DaoProjeto excluir(Projeto p) {
		return this.abrirTransacao().remover(p).fecharTransacao();
	}
	public List<Projeto> obterTodos() {
		String jpql = "SELECT f FROM Projeto p";
		return em.createQuery(jpql, Projeto.class).getResultList();
	}
	public Projeto buscarPorId(Integer id) {
		return em.find(Projeto.class, id);
	}
	public List<Projeto> buscarPorNome(String nome) {
		String jpql = "SELECT f FROM Projeto p WHERE p.nome LIKE :nome";
		TypedQuery<Projeto> consulta = em.createQuery(jpql, Projeto.class);
		consulta.setParameter("nome", "%" + nome + "%");
		return consulta.getResultList();
	}

	public void fechar() {
		em.close();
	}

}
