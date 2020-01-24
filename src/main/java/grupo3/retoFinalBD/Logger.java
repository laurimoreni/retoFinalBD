package grupo3.retoFinalBD;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class Logger {
	private static Logger logger;
	private LocalDateTime fecha;
	
	private Logger() {
		this.fecha = LocalDateTime.now().withNano(0);
	}

	public static Logger getSingletonInstance() {
		if (logger == null) {
			logger = new Logger();
		}
		return logger;
	}

	public void escribirLog(LogginLevels level, String clase, String metodo, String motivo) {
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
			printW.println(fecha.toLocalDate() + " | " + fecha.toLocalTime() + " | " + level + " | " + clase + " | " + metodo + " | " + motivo);
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
				logger.escribirLog(LogginLevels.ERROR, getClass().getName(), getClass().getEnclosingMethod().getName(), "Error al cerrar fichero");
			}
		}

	}

}
