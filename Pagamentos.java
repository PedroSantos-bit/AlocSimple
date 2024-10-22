package alocSimple;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pagamentos {
	
	private Double pagamentos;
	private LocalDateTime diaPagamento;
	
	DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
			
	public Pagamentos() {
		
	}
	
	public Pagamentos(Double pagamentos, LocalDateTime diaPagamento) {
		this.pagamentos=pagamentos;
		this.diaPagamento=diaPagamento;
	}
	
	@Override
	public String toString() {
		return this.diaPagamento.format(formato) + " - " + String.format("%.2f", this.pagamentos) + "R$";
	}
	

}
