package grupo3.retoFinalBD;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import grupo3.retoFinalBD.vista.VentanaPpal;

public class Principal {

	public VentanaPpal vista;
	public Logger logger;
	private CargarProvincias cargarProvincias;
	private LeerFicheros leer;
	private ArrayList<Provincia> provincias;
	private ArrayList<Alojamiento> alojamientos;
	private ArrayList<URL> fuentes;
	private LectorXML lectorXML;
	private LectorJSON json;

	public Principal(VentanaPpal vista) {
		this.vista = vista;
		this.logger = Logger.getSingletonInstance();
		this.cargarProvincias = new CargarProvincias();
		this.leer = new LeerFicheros();
		this.lectorXML = new LectorXML();
		this.provincias = new ArrayList<Provincia>();
		this.alojamientos = new ArrayList<Alojamiento>();
		this.json = new LectorJSON(this);
	}

	public void procesoPpal() {

		// iniciar sesion hibernate
		vista.textArea.append("Conectando a la base de datos...\n");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			vista.textArea.append("Conectado!\n");
			logger.escribirLog(LogginLevels.INFO, getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName(), "Conexión a la base de datos");

			// cargar arraylist provincias
			provincias = cargarProvincias.cargarProvincias(provincias);
			guardarProvinciasBD(session);

			// Comprobar ficheros nuevos
			vista.textArea.append("Leyendo fichero de fuentes...\n");
			fuentes = leer.leerFicheroFuentes("ficheros/ficheros_origen.txt");
			if (fuentes.size() > 0) {
				if (comprobarFicheros()) {
					desactivarAlojamientos(session);
					cargarAlojamientos(session);
					actualizarAlojamientosBD(session);
					exportarJSON();
					if (subirArchivosServidor()) {
						vista.textArea.append("Archivos JSON subidos al servidor correctamente.\n");
						logger.escribirLog(LogginLevels.INFO, getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName(), "Ficheros JSON subidos al servidor correctamente");
					} else {
						vista.textArea.append("Error al subir los archivos JSON al servidor.\n");
					}

				} else {
					vista.textArea.append("No es necesario actualizar la base de datos.\n");
				}

				// commit de los datos guardados
				session.getTransaction().commit();
			} else {
				vista.textArea.append("Fichero de fuentes no econtrado o vacío.\n");
			}
			vista.textArea.append("Proceso terminado.");
			// cerrar sesion hibernate
			HibernateUtil.shutdown();

		} catch (Exception ex) {
			vista.textArea.append("Error en la conexión a la Base de Datos\n");
			logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName(), "ERROR en la conexión a la base de datos: " + ex.getMessage());
		} finally {
			if (session == null ) {
				vista.textArea.append("Error en la conexión a la Base de Datos\n");
				logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName(), " - ERROR en la conexión a la base de datos. ");
			}
			vista.btnOk.setEnabled(true);
		}
	}

	private boolean comprobarFicheros() {
		boolean actualizar = false;
		int cont = 1;
		for (URL fuente : fuentes) {
			String[] datos = fuente.toString().split("/");
			String nombreFichero = datos[5] + ".xml";
			leer.certificadosHTTPS();
			vista.textArea.append("Descargando fichero fuente " + cont + "...\n");
			leer.descargarFichero(fuente, nombreFichero);
			vista.textArea.append("Fichero fuente " + cont + " descargado.\n");
			vista.textArea.append("Comprobando fichero fuente " + cont + "...\n");
			logger.escribirLog(LogginLevels.INFO, getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName(), "Fichero fuente " + nombreFichero + " descargado");
			if (!leer.checkFicheroActualizado(nombreFichero)) {
				vista.textArea.append("Fichero fuente " + cont + " nuevo.\n");
				vista.textArea.append("Actualizando datos de fichero fuente " + cont + "...\n");
				leer.actualizarFichero(nombreFichero);
				logger.escribirLog(LogginLevels.INFO, getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName(), "Fichero fuente " + nombreFichero + " actualizado");
				actualizar = true;
			} else {
				vista.textArea.append("No es necesario actualizar el fichero fuente " + cont + ".\n");
			}
			try {
				FileUtils.forceDelete(new File("ficheros/Temp/" + nombreFichero));
			} catch (IOException ex) {
				logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName(), "ERROR fichero temporal " + nombreFichero + "no se pudo borrar");
			}
			cont++;
		}
		return actualizar;
	}

	private void guardarProvinciasBD(Session session) {
		@SuppressWarnings("unchecked")
		List<Provincia> provinciasBD = (List<Provincia>) session.createQuery("from Provincia").list();
		if (provinciasBD.size() == 0) {
			for (Provincia provincia : provincias) {
				session.save(provincia);
			}
		}
	}

	private void desactivarAlojamientos(Session session) {
		String query = "UPDATE Alojamiento a SET a.activo = 0";
		@SuppressWarnings("rawtypes")
		Query q = session.createQuery(query);
		q.executeUpdate();
	}

	private void cargarAlojamientos(Session session) {
		int cont = 1;
		for (URL fuente : fuentes) {
			String[] datos = fuente.toString().split("/");
			String nombreFichero = datos[5] + ".xml";
			File ficheroXML = new File(nombreFichero);
			vista.textArea.append("Actualizando alojamientos de fichero de fuentes " + cont + " en base de datos...\n");
			alojamientos = lectorXML.cargarAlojamientos(ficheroXML, alojamientos, provincias, leer, session);
			cont++;
		}
	}

	private void actualizarAlojamientosBD(Session session) {
		if (alojamientos.size() > 0) {
			for (Alojamiento alojamiento : alojamientos) {
				session.saveOrUpdate(alojamiento);
			}
		}
	}

	private void exportarJSON() {
		vista.textArea.append("Exportando JSON...\n");
		json.exportarAlojamientos(alojamientos);
		json.arrayToJson(provincias, "provincias.json");
		vista.textArea.append("JSON exportado.\n");
		logger.escribirLog(LogginLevels.INFO, getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName(), "Fichero JSON exportado");
	}

	private boolean subirArchivosServidor() {
		FTPConnection ftp = new FTPConnection();
		boolean archivosSubidos = true;

		vista.textArea.append("Subiendo archivos JSON al servidor...\n");
		File[] ficherosJSON = new File("ficheros/").listFiles();

		for (File fich : ficherosJSON) {
			if (FilenameUtils.getExtension(fich.getName()).equals("json")) {
				if (!ftp.subirArchivo(fich))
					archivosSubidos = false;
				logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {}.getClass().getEnclosingMethod().getName(), "ERROR al subir el fichero " + fich + " al servidor");
			}
		}

		return archivosSubidos;
	}

}
