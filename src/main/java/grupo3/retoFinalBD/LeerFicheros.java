package grupo3.retoFinalBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;

import grupo3.retoFinalBD.vista.VentanaPpal;


public class LeerFicheros {
	private VentanaPpal vista;
	private Logger logger;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");
	
	public LeerFicheros(VentanaPpal vista) {
		this.vista = vista;
		this.logger = Logger.getSingletonInstance();
	}
	
	/**
	 * Lee el archivo sacado de cada URL, las cuales estan guardadas en el array fuentes
	 * @author Elorrieta Errekamari
	 *
	 */
	public ArrayList<Alojamiento> leerFicheroXML(ArrayList<Alojamiento> alojamientos, ArrayList<Provincia> provincias) {		
		LectorXML miLectorXML = new LectorXML();	
		ArrayList<URL> fuentes = leerFicheroFuentes();
		
		if (fuentes.size() > 0) {
			int cont = 1;
			ArrayList<String> ficherosNoActualizados = new ArrayList<String>();
			
			for (URL fuente: fuentes) {
				String[] datos = fuente.toString().split("/");
				String nombreFichero = datos[5] + ".xml";
				certificadosHTTPS();
				vista.textArea.append("Descargando fichero fuente " + cont + "...\n");
				descargarFichero(fuente, nombreFichero);
				vista.textArea.append("Fichero fuente " + cont + " descargado.\n");
				vista.textArea.append("Comprobando fichero fuente " + cont + "...\n");
				logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - Fichero fuente " + datos[5] + ".xml descargado.");
				if (!ficheroActualizado(nombreFichero)) {
					vista.textArea.append("Fichero fuente " + cont + " nuevo.\n");
					vista.textArea.append("Actualizando datos de fichero fuente " + cont + "...\n");
					actualizarFichero(nombreFichero);
					logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - Fichero fuente " + datos[5] + ".xml actualizado.");
					File ficheroXML = new File(nombreFichero);
					alojamientos = miLectorXML.CargarAlojamientos(ficheroXML, alojamientos, provincias, this);

				} else {
					vista.textArea.append("No es necesario actualizar el fichero fuente " + cont + ".\n");
				}
				cont ++;
			}
		}
		return alojamientos;
	}
	/**
	 * Crea un array con las URLs necesarias y las guarda en ficheros.txt
	 * @return
	 */
	public ArrayList<URL> leerFicheroFuentes() {
		ArrayList<URL> fuentes = new ArrayList<URL>();
		File origenes = new File("ficheros.txt");
		String linea = null;
		BufferedReader br = null;
		
		vista.textArea.append("Leyendo fichero de fuentes...\n");
		
		if (origenes != null) {
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(origenes.getPath())));
				
				while ((linea= br.readLine()) != null) {
					fuentes.add(new URL(linea));
				}
			} catch (FileNotFoundException fnfEx) {
				fnfEx.printStackTrace();
			} catch (IOException ioEx) {
				ioEx.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
		return fuentes; 
	}
	
	/**
	 * Habilita todos los certificados para poder descargar desde HTTPS 
	 */
	public void certificadosHTTPS () {
		TrustManager[] trustAllCerts = new TrustManager[]{
		    new X509TrustManager() {
		        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            return null;
		        }
		        public void checkClientTrusted(
		            java.security.cert.X509Certificate[] certs, String authType) {
		        }
		        public void checkServerTrusted(
		            java.security.cert.X509Certificate[] certs, String authType) {
		        }
		    }
		};

		// Activate the new trust manager
		try {
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
		}
	}
	
	public void descargarFichero (URL fuente, String nombre) {
		File fichero = null;
		FileOutputStream fos = null;
		
		try {
			// Comprobar que existe la carpeta temporal
			Path ruta = Paths.get("Temp");
			fichero = new File(ruta.toString() + "/" + nombre);
			if (!Files.exists(ruta)) {	
				fichero.getParentFile().mkdir();
			}
			ReadableByteChannel rbc = Channels.newChannel(fuente.openStream());
			fos = new FileOutputStream(fichero.getPath());
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		
		fichero = null;
	}
	
	public boolean ficheroActualizado(String nombre) {
		File ficheroViejo = new File(nombre);
		File ficheroNuevo = new File("Temp/" + nombre);

		boolean actualizado = false;
		
		if (ficheroViejo.isFile() && ficheroNuevo.isFile()) {
			
			try {
				actualizado = FileUtils.contentEquals(ficheroNuevo, ficheroViejo);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return actualizado;
		
	}
	
	public void actualizarFichero(String nombre) {
		File ficheroViejo = FileUtils.getFile(nombre);
		File ficheroNuevo = FileUtils.getFile("Temp/" + nombre);
		
		if (ficheroViejo.isFile()) {
			ficheroViejo.delete();
		}
		
		if (ficheroNuevo.isFile()) {
			try {
				FileUtils.copyFile(ficheroNuevo, ficheroViejo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
