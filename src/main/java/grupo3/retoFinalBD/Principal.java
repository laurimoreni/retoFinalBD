package grupo3.retoFinalBD;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;


import grupo3.retoFinalBD.vista.VentanaPpal;

public class Principal {
	private VentanaPpal vista;
	
	public Principal (VentanaPpal vista) {
		this.vista = vista;
	}
	
	public void procesoPpal() {
		// iniciar sesion hibernate
		vista.textArea.append("Conectando a la base de datos...\n");
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			vista.textArea.append("Conectado!\n");
			
			CargarProvincias cargarProvincias = new CargarProvincias();
			ArrayList<Provincia> provincias = new ArrayList<Provincia>();
			provincias = cargarProvincias.cargarProvincias(provincias);
			
			// leer ficheros XML
			LeerFicheros leer = new LeerFicheros(vista);
			ArrayList<Alojamiento> alojamientos = new ArrayList<Alojamiento>();
			alojamientos = leer.leerFicheroXML(alojamientos, provincias);
			
			// guardar datos en BD
			//Comprobar si ya están las provincias en la BD
			List<Provincia> provinciasBD = session.createQuery("from Provincia").list();
			if (provinciasBD.size() == 0) {
				
				for(Provincia provincia: provincias) {
					session.save(provincia);
				}
			}
			
			for (Alojamiento alojamiento : alojamientos) {
				session.save(alojamiento);
			}
			session.getTransaction().commit();
			
			// leer datos de la BD
			// LecturaBD lectur = new LecturaBD();
			
			// escribir los datos en archivo JSON
			vista.textArea.append("Exportando JSON...\n");
			LectorJSON json = new LectorJSON();
			json.convertirAJson(alojamientos);
			vista.textArea.append("JSON exportado.\n");
			// cerrar sesion hibernate
			HibernateUtil.shutdown();
		} catch (ExceptionInInitializerError ex) {
			vista.textArea.append("Error en la conexión a la Base de Datos\n");
		} finally {
			vista.btnOk.setEnabled(true);
		}
	}
			
}
