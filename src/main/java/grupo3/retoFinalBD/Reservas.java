package grupo3.retoFinalBD;

import java.sql.Date;

public class Reservas {

	private int id;
	private String dni;
	private Date fecha;
	private String alojamiento;
	private int personas;
	
	public Reservas() {
		super();
	}

	public Reservas(int id, String dni, Date fecha, String alojamiento, int personas) {
		super();
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
