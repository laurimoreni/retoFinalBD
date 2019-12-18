package grupo3.retoFinalBD;

import java.util.HashSet;
import java.util.Set;

public class Provincia {

	private int id;
	private String nombre;

	public Provincia() {
	}
	
	public Provincia(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}


