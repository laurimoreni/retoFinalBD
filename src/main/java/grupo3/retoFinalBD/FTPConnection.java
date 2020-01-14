package grupo3.retoFinalBD;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPConnection {
	private String servidorFTP = "188.213.5.150";
	private String user = "ldmj";
	private String pass = "ladamijo";
	private int port = 21;
	
	FTPClient ftpClient;
	
	public boolean subirArchivo(File ficheroLocal) {
		boolean resultado = false;
		InputStream is = null;
		
		ftpClient = new FTPClient();
		
		try {
			ftpClient.connect(servidorFTP, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			String nombreRemoto = ficheroLocal.getName();
			String rutaRemota = ficheroLocal.getParent();
			
			if (!checkDirectoryExists(rutaRemota)) {
				ftpClient.makeDirectory(rutaRemota);
			}
			
			ftpClient.changeWorkingDirectory(rutaRemota);
			
			is = new FileInputStream(ficheroLocal);
			resultado = ftpClient.storeFile(nombreRemoto, is);
			
			
		} catch (Exception e) {
			e.printStackTrace();
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
