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

	public ArrayList<Alojamiento> CargarAlojamientos(File archivo, ArrayList<Alojamiento> alojamientos){
		Formatos formato = new Formatos();
		ArrayList<Provincia> provincias = new ArrayList<Provincia>();
		int id = 0;
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
					alojamiento.setMunicipality(elemento.getElementsByTagName("municipalitiy").item(0).getTextContent());
					if(i == 0) {
						provincias = CargarProvincias(elemento.getElementsByTagName("territory").item(0).getTextContent(), provincias, id);
						id++;
					} else {
						for(Provincia p: provincias) {
							if(p.getNombre().equals(elemento.getElementsByTagName("territory").item(0).getTextContent())) {
								alojamiento.setProvincia(p);
							} else {
								provincias = CargarProvincias(elemento.getElementsByTagName("territory").item(0).getTextContent(), provincias, id);
								id++;
							}
						}
					}
					alojamiento.setLatwgs84(Float.parseFloat(elemento.getElementsByTagName("latwgs84").item(0).getTextContent()));
					alojamiento.setLonwgs84(Float.parseFloat(elemento.getElementsByTagName("lonwgs84").item(0).getTextContent()));
					alojamiento.setPostalcode(Integer.parseInt(elemento.getElementsByTagName("postalcode").item(0).getTextContent()));
					alojamiento.setCapacity(Integer.parseInt(elemento.getElementsByTagName("capacity").item(0).getTextContent()));
					alojamiento.setRestaurant(Integer.parseInt(elemento.getElementsByTagName("restaurant").item(0).getTextContent()));
					alojamiento.setStore(Integer.parseInt(elemento.getElementsByTagName("store").item(0).getTextContent()));
					alojamiento.setAutocaravana(Integer.parseInt(elemento.getElementsByTagName("autocaravana").item(0).getTextContent()));
					
					alojamientos.add(alojamiento);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return alojamientos;
	}
	
	public ArrayList<Provincia> CargarProvincias(String nombre, ArrayList<Provincia> provincias, int id){
		Provincia provincia = new Provincia();
		
		provincia.setNombre(nombre);
		provincia.setId(id);
		provincias.add(provincia);
		return provincias;
	}
}