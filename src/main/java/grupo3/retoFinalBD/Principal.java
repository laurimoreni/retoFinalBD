package grupo3.retoFinalBD;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import grupo3.retoFinalBD.vista.VentanaPpal;

public class Principal {
	
	private VentanaPpal vista;
	private Logger logger;
	private SimpleDateFormat dateFormat;
	private CargarProvincias cargarProvincias;
	private LeerFicheros leer;
	private ArrayList<Provincia> provincias;
	private ArrayList<Alojamiento> alojamientos;
	private ArrayList<URL> fuentes;
	private LectorXML lectorXML;
	
	public Principal (VentanaPpal vista) {
		this.vista = vista;
		this.logger = Logger.getSingletonInstance();
		this.dateFormat = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");
		this.cargarProvincias = new CargarProvincias();
		this.leer = new LeerFicheros();
		this.lectorXML = new LectorXML();
		this.provincias = new ArrayList<Provincia>();
		this.alojamientos = new ArrayList<Alojamiento>();
	}
	
	public void procesoPpal() {
		
		// iniciar sesion hibernate
		vista.textArea.append("Conectando a la base de datos...\n");
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			session.beginTransaction();
			vista.textArea.append("Conectado!\n");
			logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - Conexión a la base de datos.");
			
			// cargar arraylist provincias
			provincias = cargarProvincias.cargarProvincias(provincias);
			
			// guardar provincias en BD
			@SuppressWarnings("unchecked")
			List<Provincia> provinciasBD = (List<Provincia>) session.createQuery("from Provincia").list();
			if (provinciasBD.size() == 0) {
				for(Provincia provincia: provincias) {
					session.save(provincia);
				}
			}

			// Comprobar ficheros nuevos
			vista.textArea.append("Leyendo fichero de fuentes...\n");
			fuentes = leer.leerFicheroFuentes("ficheros/ficheros_origen.txt");
			boolean actualizar = false;
			if (fuentes.size() > 0) {
				int cont = 1;
				for (URL fuente: fuentes) {
					String[] datos = fuente.toString().split("/");
					String nombreFichero = datos[5] + ".xml";
					leer.certificadosHTTPS();
					vista.textArea.append("Descargando fichero fuente " + cont + "...\n");
					leer.descargarFichero(fuente, nombreFichero);
					vista.textArea.append("Fichero fuente " + cont + " descargado.\n");
					vista.textArea.append("Comprobando fichero fuente " + cont + "...\n");
					logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - Fichero fuente " + datos[5] + ".xml descargado.");
					if (!leer.checkFicheroActualizado(nombreFichero)) {
						vista.textArea.append("Fichero fuente " + cont + " nuevo.\n");
						vista.textArea.append("Actualizando datos de fichero fuente " + cont + "...\n");
						leer.actualizarFichero(nombreFichero);
						logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - Fichero fuente " + datos[5] + ".xml actualizado.");
						actualizar = true;
					} else {
						vista.textArea.append("No es necesario actualizar el fichero fuente " + cont + ".\n");
					}
					cont ++;
				}
			}
			
			// cargar arraylist alojamientos
			if (actualizar) {
				// Borrar tabla alojamientos
				String query = "DELETE from Alojamiento";
				@SuppressWarnings("rawtypes")
				Query q = session.createQuery(query);
				q.executeUpdate();
				
				for (URL fuente: fuentes) {
					String[] datos = fuente.toString().split("/");
					String nombreFichero = datos[5] + ".xml";
					File ficheroXML = new File(nombreFichero);
					alojamientos = lectorXML.cargarAlojamientos(ficheroXML, alojamientos, provincias, leer, session);
				}
			
				// guardar alojamientos en BD
				Session session2 = HibernateUtil.getSessionFactory().openSession();
				session2.beginTransaction();
				if (alojamientos.size() > 0) {
					
					for (Alojamiento alojamiento: alojamientos) {
						session2.save(alojamiento);
					}
				}
				session2.getTransaction().commit();
			}
			
			// commit de los datos guardados
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
			
		} catch (Exception ex) {
			vista.textArea.append("Error en la conexión a la Base de Datos\n");
			logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - ERROR en la conexión a la base de datos.");
		} finally {
			vista.btnOk.setEnabled(true);
		}
	}

}
