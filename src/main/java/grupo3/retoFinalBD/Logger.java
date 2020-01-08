package grupo3.retoFinalBD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private static Logger logger;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");

	public static Logger getSingletonInstance() {
		if (logger == null) {
			logger = new Logger();
		}
		return logger;
	}

	public void escribirLog(String mensaje) {
		File f = null;
		BufferedWriter writer = null;
		PrintWriter printW = null;

		try {
			f = new File("Log.txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), StandardCharsets.UTF_8));
			printW = new PrintWriter(writer);
			printW.println(mensaje);
		} catch (IOException e1) {

		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (printW != null) {
					printW.close();
				}
			} catch (Exception e2) {
				logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - Error al cerrar fichero.");
			}
		}

	}

}
