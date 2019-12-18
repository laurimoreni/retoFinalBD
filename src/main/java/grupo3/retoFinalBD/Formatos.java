package grupo3.retoFinalBD;

import java.util.ArrayList;

public class Formatos {
	
	/**
	 * Quita los espacios del texto enviado por parametro y lo convierte de un String a un int
	 * @param texto
	 * @return
	 */
	public int formatoNumero(String texto) {
		
		texto.replace("\\s","");
		
		return Integer.parseInt(texto);
	}
	
	/**
	 * Separa el texto que contine una , y lo entroduce en un arraylist
	 * @param texto
	 * @return resultado
	 */
	public ArrayList<String> formatoMarks (String texto){
		ArrayList<String> resultado = new ArrayList<String>();
		
		if (texto.contains(",")) {
			String[] retexto = texto.split(",");
			for(int i=0; i < retexto.length; i++) {
				resultado.add(retexto[i]);
			}
		}
		return resultado;
	}
}
