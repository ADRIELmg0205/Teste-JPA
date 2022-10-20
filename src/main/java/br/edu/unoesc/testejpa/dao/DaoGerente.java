package br.edu.unoesc.testejpa.dao;

import java.util.List;
import br.edu.unoesc.testejpa.model.Departamento;
import br.edu.unoesc.testejpa.model.Gerente;
import br.edu.unoesc.testejpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class DaoGerente {
	private EntityManager em;

	public DaoGerente() {
		em = JPAUtil.getEntityManager();
	}
	// métodos privados
	private DaoGerente abrirTransacao() {
		em.getTransaction().begin();
		return this;
	}
	private DaoGerente fecharTransacao() {
		em.getTransaction().commit();
		return this;
	}
	private DaoGerente incluir(Gerente g) {
		em.persist(g);
		return this;
	}
	private DaoGerente remover(Gerente g) {
		em.remove(g);
		return this;
	}
	// métodos públicos
	public DaoGerente salvar(Gerente g) {
		return this.abrirTransacao().incluir(g).fecharTransacao();
	}
	public DaoGerente excluir(Gerente g) {
		return this.abrirTransacao().remover(g).fecharTransacao();
	}
	public List<Gerente> obterTodos() {
		String jpql = "SELECT d FROM Departamento d";
		return em.createQuery(jpql, Gerente.class).getResultList();
	}
	public Gerente buscarPorId(Integer id) {
		return em.find(Gerente.class, id);
	}
	public List<Gerente> buscarPorNome(String nome) {
		String jpql = "SELECT d FROM Gerente d WHERE d.nome LIKE :nome";
		TypedQuery<Gerente> consulta = em.createQuery(jpql, Gerente.class);
		consulta.setParameter("nome", "%" + nome + "%");
		return consulta.getResultList();
	}
	public void fechar() {
		em.close();
	}
}
