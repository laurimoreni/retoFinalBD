package grupo3.retoFinalBD;

import java.util.ArrayList;

import com.google.gson.Gson;

public class LectorJSON {
	/**
	 * La informacion enviada dentro del arraylist lo convierte a json
	 * @param array
	 */
	public void convertirAJson(ArrayList<Alojamiento> array) {
		
		if(!array.isEmpty()) {
			for(int i = 0; i < array.size(); i++) {
				//String json = new Gson().toJson(array.get(i));
				Gson gson = new Gson();        
			    System.out.println(gson.toJson(array.get(i)));
			}
		}
	}
}
