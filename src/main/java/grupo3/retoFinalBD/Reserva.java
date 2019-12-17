package grupo3.retoFinalBD;

import java.sql.Date;

public class Reserva {

	private int id;
	private String dni;
	private Date fecha;
	private String alojamiento;
	private int personas;
	
	public Reserva() {
	}

	public Reserva(int id, String dni, Date fecha, String alojamiento, int personas) {
		this.id = id;
		this.dni = dni;
		this.fecha = fecha;
		this.alojamiento = alojamiento;
		this.personas = personas;
	}

	public int getId() {
		return id;
	}

	public String getDni() {
		return dni;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getAlojamiento() {
		return alojamiento;
	}

	public int getPersonas() {
		return personas;
	}
	
}
