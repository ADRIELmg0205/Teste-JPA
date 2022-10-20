package br.edu.unoesc.testejpa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "projetos")
public class Projeto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 50, nullable = false)
	private String nome;

	@ManyToMany(mappedBy = "projetosFuncionario", 
                        cascade = CascadeType.ALL, fetch = FetchType.EAGER
                     )
	private List<Funcionario> funcionarios;

	public Projeto() {
	}

public void adicionarFuncionario(Funcionario funcionario) {
		if (funcionario != null && !this.getFuncionarios().contains(funcionario)) {
			this.funcionarios.add(funcionario);

			if (!funcionario.getProjetos().contains(this)) {
				funcionario.getProjetos().add(this);
			}
		}
	}

	public List<Funcionario> getFuncionarios() {
		if (this.funcionarios == null) {
			this.funcionarios = new ArrayList<>();
		}
		return funcionarios;
	}

	public Projeto(Integer id, String nome, List<Funcionario> funcionarios) {
		super();
		this.id = id;
		this.nome = nome;
		this.funcionarios = funcionarios;
	}

	
	public Projeto(String nome) {
		super();
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", nome=" + nome + ", funcionarios=" + funcionarios + "]";
	}

	


}
