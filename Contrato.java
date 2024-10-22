package alocSimple;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Contrato {
	private String dataAlocacao;
	private Double valorDoContrato;
	private Integer tempoDeAlocacao;
	public String formatoContrato; //aqui a dúvida.
	
	
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public Contrato() {
		
	}

	public Contrato(LocalDate dataAlocacao,Integer tempoDeAlocacao) {
		this.dataAlocacao = dataAlocacao.format(formato);
		this.tempoDeAlocacao=tempoDeAlocacao;
	}

	public String getDataAlocacao() {
		return this.dataAlocacao;
	}
	
	public String getDataVencimento() {
		LocalDate data = LocalDate.parse(this.dataAlocacao,formato);
		
		if(this.tempoDeAlocacao == 1) {
			String dataDefinida = data.plusDays(182).format(formato);
			formatoContrato = "6 meses";
			return dataDefinida;
		}
		
		String dataDefinida = data.plusDays(365).format(formato);
		formatoContrato = "12 meses";
	return dataDefinida;
	}

}
