package br.edu.unoesc.testejpa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionarios")
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 50, nullable = false)
	private String nome;

	@Column(name = "data_nascimento", nullable = false)
	private Date dataNascimento;

	@Column(precision = 12, scale = 2, nullable = false)
	private BigDecimal salario;

	@ManyToOne
	private Cargo cargo;
	
	@ManyToMany 
	(cascade = CascadeType.ALL, 
                         fetch =   FetchType.EAGER)
	@JoinTable(name = "funcionarios_projeto",
                        joinColumns = @JoinColumn(name = "id_funcionario"),
                        inverseJoinColumns = @JoinColumn(name = "id_projeto"))
	private List<Projeto> projetosFuncionario;

	public Funcionario() {
	}

		
		public Funcionario(String nome, Date dataNascimento, BigDecimal salario, Cargo cargo) {
			this.nome = nome;
			this.dataNascimento = dataNascimento;
			this.salario = salario;
			cargo.adicionarFuncionario(this);
		}
		
		
		
		public Funcionario(String nome, Date dataNascimento, BigDecimal salario) {
			super();
			this.nome = nome;
			this.dataNascimento = dataNascimento;
			this.salario = salario;
		}


		public void adicionarProjeto(Projeto projeto) {
			if (projeto != null && !this.getProjetos().contains(projeto)) {
				this.projetosFuncionario.add(projeto);
				if (!projeto.getFuncionarios().contains(this)) {
					projeto.getFuncionarios().add(this);
				}
			}
		}

		public List<Projeto> getProjetos() {
			if (projetosFuncionario == null) {
				projetosFuncionario = new ArrayList<>();
			}
			return projetosFuncionario;
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

		public Date getDataNascimento() {
			return dataNascimento;
		}

		public void setDataNascimento(Date dataNascimento) {
			this.dataNascimento = dataNascimento;
		}

		public BigDecimal getSalario() {
			return salario;
		}

		public void setSalario(BigDecimal salario) {
			this.salario = salario;
		}

		public Cargo getCargo() {
			return cargo;
		}

		public void setCargo(Cargo cargo) {
			this.cargo = cargo;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		@Override
		public String toString() {
			return "Funcionario [id=" + id + ", nome=" + nome + ", dataNascimento=" + dataNascimento + ", salario="
					+ salario + ", cargo=" + cargo + "]";
		}
	}
