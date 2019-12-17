package grupo3.retoFinalBD;

public class Usuario {

	
	private String dni;
	private String nombre;
	private String apellidos;
	private String contrasena;
	private int telefono;
	
	public Usuario(String dni, String nombre, String apellidos, String contrasena, int telefono) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contrasena = contrasena;
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getContrasena() {
		return contrasena;
	}

	public int getTelefono() {
		return telefono;
	}
}