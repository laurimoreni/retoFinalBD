package grupo3.retoFinalBD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;


import grupo3.retoFinalBD.vista.VentanaPpal;

public class Principal {
	private VentanaPpal vista;
	private Logger logger;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");
	
	public Principal (VentanaPpal vista) {
		this.vista = vista;
		this.logger = Logger.getSingletonInstance();
	}
	
	public void procesoPpal() {
		// iniciar sesion hibernate
		vista.textArea.append("Conectando a la base de datos...\n");
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			vista.textArea.append("Conectado!\n");
			logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - Conexión a la base de datos.");
			
			CargarProvincias cargarProvincias = new CargarProvincias();
			ArrayList<Provincia> provincias = new ArrayList<Provincia>();
			provincias = cargarProvincias.cargarProvincias(provincias);
			
			// leer ficheros XML
			LeerFicheros leer = new LeerFicheros(vista);
			ArrayList<Alojamiento> alojamientos = new ArrayList<Alojamiento>();
			alojamientos = leer.leerFicheroXML(alojamientos, provincias);
			
			// guardar datos en BD
			//Comprobar si ya están las provincias en la BD
			@SuppressWarnings("unchecked")
			List<Provincia> provinciasBD = (List<Provincia>) session.createQuery("from Provincia").list();
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
			logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - Fichero JSON exportado.");
			// cerrar sesion hibernate
			HibernateUtil.shutdown();
		} catch (ExceptionInInitializerError ex) {
			vista.textArea.append("Error en la conexión a la Base de Datos\n");
			logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - ERROR en la conexión a la base de datos.");
		} finally {
			vista.btnOk.setEnabled(true);
		}
	}
			
}
