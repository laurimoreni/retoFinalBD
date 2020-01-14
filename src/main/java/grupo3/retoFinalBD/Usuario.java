package grupo3.retoFinalBD;

public class Usuario {

	
	private String dni;
	private String nombre;
	private String apellidos;
	private String email;
	private String contrasena;
	private int telefono;
	private int administrador;
	
	public Usuario() {
		
	}
	
	public Usuario(String dni, String nombre, String apellidos, String email, String contrasena, int telefono, int administrador) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.contrasena = contrasena;
		this.telefono = telefono;
		this.administrador = administrador;
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

	public String getEmail() {
		return email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public int getTelefono() {
		return telefono;
	}
	
	public int getAdministrador() {
		return administrador;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	
	public void setAdministrador(int administrador) {
		this.administrador = administrador;
	}
}