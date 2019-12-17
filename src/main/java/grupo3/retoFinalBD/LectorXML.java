package grupo3.retoFinalBD;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LectorXML {

	public ArrayList<Alojamiento> CargarAlojamientos(String path, ArrayList<Alojamiento> alojamientos){
		File archivo = new File(path);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document documento = builder.parse(archivo);
			
			documento.getDocumentElement().normalize();
			NodeList nodos = documento.getElementsByTagName("row");
			for(int i = 0; i < nodos.getLength(); i++) {
				Node nodo = nodos.item(i);
				if(nodo.getNodeType() == Node.ELEMENT_NODE) {
					Element elemento = (Element) nodo;
					Alojamiento alojamiento = new Alojamiento();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return alojamientos;
	}
}