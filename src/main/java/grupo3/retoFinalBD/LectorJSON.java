package grupo3.retoFinalBD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

public class LectorJSON {
	
	private Principal ppal;
	
	public LectorJSON(Principal ppal) {
		this.ppal = ppal;
	}
	
	/**
	 * Convierte el arraylist de alojamientos a multiples arraylist mas pequeños de maximo 100 alojamientos y los escribe a ficheros JSON
	 * @param array
	 */
	public void exportarAlojamientos(ArrayList<Alojamiento> alojamientos) {
		ArrayList<Alojamiento> aloj = null;
		String filename = null;
		int size = alojamientos.size(), init = 0, fin = 99, count = 1;
		while (fin < size) {
			aloj = new ArrayList<Alojamiento>(alojamientos.subList(init, fin + 1));
			filename = "alojamientos" + count + ".json";
			arrayToJson(aloj, filename);
			ppal.vista.textArea.append("Fichero " + filename + " creado.\n");
			ppal.logger.escribirLog(LogginLevels.INFO, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "Fichero " + filename + " creado");
			count++;
			init = init + 100;
			fin = fin + 100;
			if (fin > size) {
				fin = size;
				filename = "alojamientos" + count + ".json";
				aloj = new ArrayList<Alojamiento>(alojamientos.subList(init, fin));
				arrayToJson(aloj, filename);
				ppal.vista.textArea.append("Fichero " + filename + " creado.\n");
				ppal.logger.escribirLog(LogginLevels.INFO, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "Fichero " + filename + " creado");
			}
		}
	}
	
	/**
	 * La informacion enviada dentro del arraylist lo convierte a json
	 * @param array
	 */
	public void arrayToJson(ArrayList<?> array, String filename) {
		if(!array.isEmpty()) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String ruta = "ficheros" + File.separator + filename;
			File file = new File(ruta);
			FileWriter writer = null;
			file.getParentFile().mkdirs();
			try {
				writer = new FileWriter(file);
				gson.toJson(array, writer);
				writer.close();
			} catch (JsonIOException e) {
				ppal.logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "No se ha podido escribir el archivo Json " + filename);
			} catch (IOException e) {
				ppal.logger.escribirLog(LogginLevels.ERROR, getClass().getName(), new Object() {} .getClass().getEnclosingMethod().getName(), "Operacion E/S fallida o interrunpida");
			}
		}
	}
}
