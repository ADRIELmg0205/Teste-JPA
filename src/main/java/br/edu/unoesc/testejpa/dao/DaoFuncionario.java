package br.edu.unoesc.testejpa.dao;

import java.util.List;
import br.edu.unoesc.testejpa.model.Funcionario;
import br.edu.unoesc.testejpa.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class DaoFuncionario {
	private EntityManager em;

	public DaoFuncionario() {
		em = JPAUtil.getEntityManager();
	}
	// métodos privados
	private DaoFuncionario abrirTransacao() {
		em.getTransaction().begin();
		return this;
	}
	private DaoFuncionario fecharTransacao() {
		em.getTransaction().commit();
		return this;
	}
	private DaoFuncionario incluir(Funcionario f) {
		em.persist(f);
		return this;
	}
	private DaoFuncionario remover(Funcionario f) {
		em.remove(f);
		return this;
	}
	// métodos públicos
	public DaoFuncionario salvar(Funcionario f) {
		return this.abrirTransacao().incluir(f).fecharTransacao();
	}
	public DaoFuncionario excluir(Funcionario f) {
		return this.abrirTransacao().remover(f).fecharTransacao();
	}
	public List<Funcionario> obterTodos() {
		String jpql = "SELECT f FROM Funcionario f";
		return em.createQuery(jpql, Funcionario.class).getResultList();
	}
	public Funcionario buscarPorId(Integer id) {
		return em.find(Funcionario.class, id);
	}
	public List<Funcionario> buscarPorNome(String nome) {
		String jpql = "SELECT f FROM Funcionario f WHERE f.nome LIKE :nome";
		TypedQuery<Funcionario> consulta = em.createQuery(jpql, Funcionario.class);
		consulta.setParameter("nome", "%" + nome + "%");
		return consulta.getResultList();
	}

	public void fechar() {
		em.close();
	}

}
