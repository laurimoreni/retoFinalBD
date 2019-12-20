package grupo3.retoFinalBD;

import java.util.ArrayList;

public class CargarProvincias {	
	public ArrayList<Provincia> cargarProvincias(ArrayList<Provincia> provincias) {		
		provincias.add(new Provincia(1, "Bizkaia/Vizcaya"));
		provincias.add(new Provincia(2, "Araba/Álava"));
		provincias.add(new Provincia(3, "Gipuzkoa/Guipuzcoa"));
		
		return provincias;
	}
}
