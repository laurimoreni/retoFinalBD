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
import java.nio.channels.FileChannel;
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
	
	private Logger logger;
	
	public LeerFicheros() {
		this.logger = Logger.getSingletonInstance();
	}
	
	/**
	 * Crea un array con las URLs necesarias y las guarda en ficheros.txt
	 * @return
	 */
	public ArrayList<URL> leerFicheroFuentes(String filename) {
		ArrayList<URL> fuentes = new ArrayList<URL>();
		File origenes = new File(filename);
		String linea = null;
		BufferedReader br = null;
		if (origenes != null) {
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(origenes.getPath())));
				while ((linea= br.readLine()) != null) {
					fuentes.add(new URL(linea));
				}
			} catch (FileNotFoundException fnfEx) {
				logger.escribirLog(LogginLevels.WARNING, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "No se ha encontrado el archivo " + filename);
			} catch (IOException ioEx) {
				logger.escribirLog(LogginLevels.WARNING, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "Operacion E/S fallida o interrunpida");
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						logger.escribirLog(LogginLevels.WARNING, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "No se ha cerrado el archivo " + filename);
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
		// Activa el nuevo trust manager
		try {
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "Error al iniciar los certificados HTTPS");
		}
	}
	
	public void descargarFichero(URL fuente, String filename) {
		File fichero = null;
		FileOutputStream fos = null;
		FileChannel fc = null;
		ReadableByteChannel rbc = null;
		try {
			// Comprobar que existe la carpeta temporal
			Path ruta = Paths.get("ficheros/Temp");
			fichero = new File(ruta.toString() + "/" + filename);
			if (!Files.exists(ruta)) {	
				fichero.getParentFile().mkdir();
			}
			rbc = Channels.newChannel(fuente.openStream());
			fos = new FileOutputStream(fichero.getPath());
			fc = fos.getChannel();
			fc.transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch(Exception e) {
			logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "Error al descargar el archivo " + filename);
		} finally {
			if (fc != null) {
				try {
					fc.close();
				} catch (IOException ex) {
					logger.escribirLog(LogginLevels.WARNING, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "No se ha cerrado el canal de lectura del archivo" + filename);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ex) {
					logger.escribirLog(LogginLevels.WARNING, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "No se ha cerrado el stream del archivo " + filename);
				}
			}
			if (rbc != null) {
				try {
					rbc.close();
				} catch (IOException ex) {
					logger.escribirLog(LogginLevels.WARNING, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "No se ha cerrado canal de bytes el archivo " + filename);
				}
			}
		}
	}
	
	public boolean checkFicheroActualizado(String filename) {
		File ficheroViejo = new File("ficheros/" + filename);
		File ficheroNuevo = new File("ficheros/Temp/" + filename);
		boolean actualizado = false;
		if (ficheroViejo.isFile() && ficheroNuevo.isFile()) {
			try {
				actualizado = FileUtils.contentEquals(ficheroNuevo, ficheroViejo);
			} catch (Exception ex) {
				logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "Error al comprobar si el archivo " + filename +  " esta actualizado");
			}
		}
		return actualizado;
	}
	
	public void actualizarFichero(String filename) {
		File ficheroViejo = FileUtils.getFile("ficheros/" + filename);
		File ficheroNuevo = FileUtils.getFile("ficheros/Temp/" + filename);
		try {
			if (ficheroViejo.exists()) {
				FileUtils.forceDelete(ficheroViejo);
			}
			if (ficheroNuevo.exists()) {				
				FileUtils.copyFile(ficheroNuevo, ficheroViejo);
			}
		} catch (IOException e) {
			logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "Error al actualizar el archivo " + filename);
		}
	}

}
