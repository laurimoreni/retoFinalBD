package grupo3.retoFinalBD;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPConnection {
	
	private FTPClient ftpClient;
	private String servidorFTP;
	private String user;
	private String pass;
	private int port;
	private Logger logger;
	
	public FTPConnection() {
		this.ftpClient = new FTPClient();
		this.servidorFTP = "188.213.5.150";
		this.user = "ldmj";
		this.pass = "ladamijo";
		this.port = 21;
		this.logger = Logger.getSingletonInstance();
	}
	
	public boolean subirArchivo(File ficheroLocal) {
		String nombreRemoto = ficheroLocal.getName();
		String rutaRemota = ficheroLocal.getParent();
		boolean resultado = false;
		InputStream is = null;
		try {
			ftpClient.connect(servidorFTP, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (!checkDirectoryExists(rutaRemota)) {
				ftpClient.makeDirectory(rutaRemota);
			}
			ftpClient.changeWorkingDirectory(rutaRemota);
			is = new FileInputStream(ficheroLocal);
			resultado = ftpClient.storeFile(nombreRemoto, is);
		} catch (IOException e) {
			logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "No se ha podido subir el archivo " + nombreRemoto + " al servidor FTP");
		}
		return resultado;
	}
	
	private boolean checkDirectoryExists(String dirPath) throws IOException {
	    ftpClient.changeWorkingDirectory(dirPath);
	    int returnCode = ftpClient.getReplyCode();
	    if (returnCode == 550) {
	        return false;
	    }
	    return true;
	}
}
