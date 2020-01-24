package grupo3.retoFinalBD;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class LeerFicharosTest {

	@Test
	public void descargarFicheroTest() throws IOException {
		LeerFicheros lf = new LeerFicheros();
		
		URL url = new URL("https://opendata.euskadi.eus/contenidos/ds_recursos_turisticos/campings_de_euskadi/opendata/alojamientos.xml");
		String nombre = "ficheroTemporal.xml";
		
		lf.certificadosHTTPS();
		lf.descargarFichero(url, nombre);
		assertEquals(Files.exists(Paths.get("ficheros/temp/" + nombre)), true);
		
		FileUtils.forceDelete( new File("ficheros/temp/" + nombre));
		
	}

}
