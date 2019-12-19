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
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;


public class LeerFicheros {
	
	ArrayList<Provincia> provincias = new ArrayList<Provincia>();
	ArrayList<String> nombreProvincia = new ArrayList<String>();
	/**
	 * Lee el archivo sacado de cada URL, las cuales estan guardadas en el array fuentes
	 * @author Elorrieta Errekamari
	 *
	 */
	public ArrayList<Alojamiento> leerFicheroXML(ArrayList<Alojamiento> alojamientos) {		
		LectorXML miLectorXML = new LectorXML();	
		ArrayList<URL> fuentes = leerFicheroFuentes();
		
		if (fuentes.size() > 0) {
			
			for (URL fuente: fuentes) {
				String[] datos = fuente.toString().split("/");
				String nombreFichero = datos[5] + ".xml";
				certificadosHTTPS();
				descargarFichero(fuente, nombreFichero);
				File fichero = new File(nombreFichero);
				alojamientos = miLectorXML.CargarAlojamientos(fichero, alojamientos, this);
				if (!ficheroActualizado(nombreFichero)) {
					//File fichero = new File(nombreFichero);
					//alojamientos = miLectorXML.CargarAlojamientos(fichero, alojamientos);
				}
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
		try {
			// Comprobar que existe la carpeta temporal
			Path ruta = Paths.get("Temp");
			fichero = new File(ruta.toString() + "/" + nombre);
			if (!Files.exists(ruta)) {	
				fichero.getParentFile().mkdir();
			}
			ReadableByteChannel rbc = Channels.newChannel(fuente.openStream());
			FileOutputStream fos = new FileOutputStream(fichero.getPath());
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean ficheroActualizado(String nombre) {
		File ficheroViejo = new File(nombre);
		File ficheroNuevo = new File("Temp/" + nombre);
		
		boolean actualizado = false;
		
		try {
			actualizado = FileUtils.contentEquals(ficheroNuevo, ficheroViejo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return actualizado;
		
	}
	
	
}
