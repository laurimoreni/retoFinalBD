package grupo3.retoFinalBD;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class LeerFicheros {
//	public ArrayList<Libro> leerFicheroXML(ArrayList<Libro> libros) {		
//		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
//		
//		ArrayList<URL> fuentes = leerFicheroFuentes();
//		
//		for (URL fuente: fuentes) {
//			File fichero = new File(fuente.getFile());
//		}
//		File fichero = elegirFichero("xml");
//		if (fichero != null) {
//		    try {
//		        SAXParser saxParser = saxParserFactory.newSAXParser();
//		        XMLHandler handler = new XMLHandler();
//		        saxParser.parse(fichero, handler);
//		        
//		        ArrayList<Libro> librosXML = handler.librosXML();
//		        
//		        for(Libro libro : librosXML)
//		            System.out.println(libro.toString());
//		        
//		        libros.addAll(librosXML);
//		        
//		    } catch (ParserConfigurationException | SAXException | IOException e) {
//		        e.printStackTrace();
//		    }
//		}
//		return libros;
//	}
	
	public ArrayList<URL> leerFicheroFuentes() {
		ArrayList<URL> fuentes = new ArrayList<URL>();
		
		
		return fuentes; 
	}
	
	
}
