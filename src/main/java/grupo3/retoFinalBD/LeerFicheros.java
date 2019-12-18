package grupo3.retoFinalBD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class LeerFicheros {
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
//				alojamientos = miLectorXML.CargarAlojamientos(fichero, alojamientos);
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
		try {
			ReadableByteChannel rbc = Channels.newChannel(fuente.openStream());
			FileOutputStream fos = new FileOutputStream(nombre);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
