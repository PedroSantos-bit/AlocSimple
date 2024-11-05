package alocSimple;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pessoa {
	private String nome;
	private Integer idade;
	private String rg;
	private String status;

	private Contrato contrato;

	private Apartamento apartamento;

	public Pessoa() {

	}

	public Pessoa(String nome, Integer idade, String rg, String status) {
		this.nome = nome;
		this.idade = idade;
		this.rg = rg;
		this.status = status;
	}

	public String getNome() {
		return this.nome;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Integer getIdade() {
		return this.idade;
	}

	public void adicionarAoApartamento(Apartamento numeroApartamento) {
		this.apartamento = numeroApartamento;
	}

	public Integer visualizarApartamento() {
		return apartamento.getNumeroApartamento();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void fazerContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public String getVencimento() {
		return contrato.getDataVencimento();
	}

	public String diaDaAlocacao() {
		return this.contrato.getDataAlocacao();
	}

	public String getFormatoContrato() {
		return contrato.formatoContrato;
	}

	public String toString() {
		return "APARTAMENTO " + apartamento.numeroApartamento + ": " + this.nome + "  |  " + this.idade + " anos  |  "
				+ this.status + "  |  " + this.rg + "  |  " + this.contrato.getDataAlocacao() + " - "
				+ this.contrato.getDataVencimento() + "\n\n";
	}

}
