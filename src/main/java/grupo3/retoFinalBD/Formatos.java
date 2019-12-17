package grupo3.retoFinalBD;

public class Formatos {
	
	public int formatoNumero(String texto) {
		
		texto.replace("\\s","");
		
		return Integer.parseInt(texto);
	}
}
