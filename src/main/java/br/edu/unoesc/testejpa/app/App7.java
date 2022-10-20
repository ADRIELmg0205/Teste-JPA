package br.edu.unoesc.testejpa.app;

import java.math.BigDecimal;
import java.util.List;

import br.edu.unoesc.testejpa.dao.DaoCargo;
import br.edu.unoesc.testejpa.dao.DaoFuncionario;
import br.edu.unoesc.testejpa.model.Cargo;
import br.edu.unoesc.testejpa.model.Funcionario;

public class App7 {

	public static void main(String[] args) {

		DaoCargo daoCargo = new DaoCargo();

		Cargo cargo1 = new Cargo("Gerente");
		Cargo cargo2 = new Cargo("Assistente");

		daoCargo.salvar(cargo1).salvar(cargo2);

		DaoFuncionario daoFuncionario = new DaoFuncionario();
		Funcionario funcionario1 = new Funcionario("Paula", java.sql.Date.valueOf("2000-04-01"),
				new BigDecimal("10000.0"), cargo1);
		Funcionario funcionario2 = new Funcionario("Ana", java.sql.Date.valueOf("2001-04-01"),
				new BigDecimal("20000.0"), cargo2);
		Funcionario funcionario3 = new Funcionario("Eduarda", java.sql.Date.valueOf("2002-04-01"),
				new BigDecimal("30000.0"), cargo2);

		daoFuncionario.salvar(funcionario1).salvar(funcionario2).salvar(funcionario3);

		System.out.println(daoFuncionario.buscarPorId(1));
		System.out.println(daoFuncionario.buscarPorId(2));

		List<Funcionario> funcionarios = daoFuncionario.obterTodos();
		System.out.println("Funcionario\tCargo");
		for (Funcionario f : funcionarios) {
			System.out.println(f.getNome() + "\t\t" + f.getCargo().getNome());
		}

		List<Cargo> cargos = daoCargo.obterTodos();
		System.out.println("Cargos e Funcionarios");
		for (Cargo c : cargos) {
			System.out.println(c.getNome());
			for (Funcionario f : c.getFuncionarios()) {
				System.out.println("\t" + f.getNome());
			}
		}

		daoCargo.fechar();
		daoFuncionario.fechar();

	}
}
