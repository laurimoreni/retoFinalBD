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
		Formatos formato = new Formatos();
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
					
					alojamiento.setSignatura(elemento.getElementsByTagName("signatura").item(0).getTextContent());
					alojamiento.setDocumentname(elemento.getElementsByTagName("documentname").item(0).getTextContent());
					alojamiento.setTurismdescription(elemento.getElementsByTagName("turismdescription").item(0).getTextContent());
					alojamiento.setLodgingtype(elemento.getElementsByTagName("lodgingtype").item(0).getTextContent());
					alojamiento.setAddress(elemento.getElementsByTagName("address").item(0).getTextContent());
					alojamiento.setPhone(formato.formatoNumero(elemento.getElementsByTagName("phone").item(0).getTextContent()));
					alojamiento.setTurismemail(elemento.getElementsByTagName("turismemail").item(0).getTextContent());
					alojamiento.setWeb(elemento.getElementsByTagName("web").item(0).getTextContent());
					alojamiento.setMarks(elemento.getElementsByTagName("marks").item(0).getTextContent());
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return alojamientos;
	}
}