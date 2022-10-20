package br.edu.unoesc.testejpa.dao;

import java.util.List;
import br.edu.unoesc.testejpa.model.Cargo;
import br.edu.unoesc.testejpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class DaoCargo {

	private EntityManager em;

	public DaoCargo() {
		em = JPAUtil.getEntityManager();
	}
	// métodos privados
	private DaoCargo abrirTransacao() {
		em.getTransaction().begin();
		return this;
	}
	private DaoCargo fecharTransacao() {
		em.getTransaction().commit();
		return this;
	}
	private DaoCargo incluir(Cargo c) {
		em.persist(c);
		return this;
	}
	private DaoCargo remover(Cargo c) {
		em.remove(c);
		return this;
	}

	// métodos públicos
	public DaoCargo salvar(Cargo c) {
		return this.abrirTransacao().incluir(c).fecharTransacao();
	}
	public DaoCargo excluir(Cargo c) {
		return this.abrirTransacao().remover(c).fecharTransacao();
	}
	public List<Cargo> obterTodos() {
		String jpql = "SELECT c FROM Cargo c";
		return em.createQuery(jpql, Cargo.class).getResultList();
	}
	public Cargo buscarPorId(Integer id) {
		return em.find(Cargo.class, id);
	}
	public List<Cargo> buscarPorNome(String nome) {
		String jpql = "SELECT c FROM Cargo c WHERE c.nome LIKE :nome";
		TypedQuery<Cargo> consulta = em.createQuery(jpql, Cargo.class);
		consulta.setParameter("nome", "%" + nome + "%");
		return consulta.getResultList();
	}

	public void fechar() {
		em.close();
	}
}
