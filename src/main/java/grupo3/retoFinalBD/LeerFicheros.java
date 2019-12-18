package grupo3.retoFinalBD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;


public class LeerFicheros {
	/**
	 * Lee el archivo sacado de cada URL, las cuales estan guardadas en el array fuentes
	 * @author Elorrieta Errekamari
	 *
	 */
	public ArrayList<Alojamiento> leerFicheroXML(ArrayList<Alojamiento> libros) {		
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		
		ArrayList<URL> fuentes = leerFicheroFuentes();
		ArrayList<Alojamiento> alojamientos = new ArrayList<Alojamiento>();
		
		if (fuentes.size() > 0) {
			for (URL fuente: fuentes) {
				File fichero = new File(fuente.getFile());
			}
		
//			File fichero = elegirFichero("xml");
//			if (fichero != null) {
//			    try {
//			        SAXParser saxParser = saxParserFactory.newSAXParser();
//			        XMLHandler handler = new XMLHandler();
//			        saxParser.parse(fichero, handler);
//			        
//			        ArrayList<Libro> librosXML = handler.librosXML();
//			        
//			        for(Libro libro : librosXML)
//			            System.out.println(libro.toString());
//			        
//			        libros.addAll(librosXML);
//			        
//			    } catch (ParserConfigurationException | SAXException | IOException e) {
//			        e.printStackTrace();
//			    }
//			}
		}
		return alojamientos;
	}
	/**
	 * Crea un array con las URLs necesarias y las guarda en ficheros.txt
	 * @return
	 */
	public ArrayList<URL> leerFicheroFuentes() {
		ArrayList<URL> fuentes = new ArrayList<URL>();
		File origenes = new File("ficheros.txt");
		String linea = null;
		
		if (origenes != null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(origenes.getPath())));
				
				while ((linea= br.readLine()) != null) {
					fuentes.add(new URL(linea));
				}
			} catch (FileNotFoundException fnfEx) {
				fnfEx.printStackTrace();
			} catch (IOException ioEx) {
				ioEx.printStackTrace();
			}
			
		}
		
		return fuentes; 
	}
	
	
}
