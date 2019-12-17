package grupo3.retoFinalBD;

import java.sql.Date;

public class Reserva {

	private int id;
	private Usuario usuario;
	private Date fecha;
	private Alojamiento alojamiento;
	private int personas;
	
	public Reserva() {
	}

	public Reserva(int id, Usuario usuario, Date fecha, Alojamiento alojamiento, int personas) {
		this.id = id;
		this.usuario = usuario;
		this.fecha = fecha;
		this.alojamiento = alojamiento;
		this.personas = personas;
	}

	public int getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public Alojamiento getAlojamiento() {
		return alojamiento;
	}

	public int getPersonas() {
		return personas;
	}
	
}
