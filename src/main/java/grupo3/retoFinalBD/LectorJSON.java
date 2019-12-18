package grupo3.retoFinalBD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

public class LectorJSON {
	/**
	 * La informacion enviada dentro del arraylist lo convierte a json
	 * @param array
	 */
	public void convertirAJson(ArrayList<Alojamiento> array) {
		
		if(!array.isEmpty()) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String ruta = "ficheros" + File.separator + "alojamientos.json";
			File file = new File(ruta);
			FileWriter writer = null;
			file.getParentFile().mkdirs();
			try {
				writer = new FileWriter(file);
				gson.toJson(array, writer);
				writer.close();
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
