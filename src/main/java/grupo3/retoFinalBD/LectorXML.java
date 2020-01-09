package grupo3.retoFinalBD;

import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LectorXML {

	public ArrayList<Alojamiento> cargarAlojamientos(File archivo, ArrayList<Alojamiento> alojamientos, ArrayList<Provincia> provincias, LeerFicheros leer){
		Formatos formato = new Formatos();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document documento = builder.parse("ficheros/" + archivo);
			
			documento.getDocumentElement().normalize();
			NodeList nodos = documento.getElementsByTagName("row");
			for(int i = 0; i < nodos.getLength(); i++) {
				Node nodo = nodos.item(i);
				if(nodo.getNodeType() == Node.ELEMENT_NODE) {
					Element elemento = (Element) nodo;
					Alojamiento alojamiento = new Alojamiento();
					try {
						alojamiento.setDocumentname(elemento.getElementsByTagName("documentname").item(0).getTextContent());
					} catch(NullPointerException e) {
						alojamiento.setDocumentname("Sin nombre");
					}
					try {
						alojamiento.setTurismdescription(elemento.getElementsByTagName("turismdescription").item(0).getTextContent());
					} catch(NullPointerException e) {
						alojamiento.setTurismdescription("Sin descripcion");
					}
					try {
						alojamiento.setLodgingtype(elemento.getElementsByTagName("lodgingtype").item(0).getTextContent());
					} catch(NullPointerException e) {
						alojamiento.setLodgingtype("Algo rural");
					}
					try {
						alojamiento.setAddress(elemento.getElementsByTagName("address").item(0).getTextContent());
					} catch(NullPointerException e) {
						alojamiento.setAddress("Norte");
					}
					try {
						alojamiento.setPhone(formato.formatoNumero(elemento.getElementsByTagName("phone").item(0).getTextContent()));
					} catch(NullPointerException e) {
						alojamiento.setPhone("555863147");
					}
					try {
						alojamiento.setTourismemail(elemento.getElementsByTagName("tourismemail").item(0).getTextContent());
					} catch(NullPointerException e) {
						alojamiento.setTourismemail("sin_tag@nosevalidarxml.com");
					}
					try {
						alojamiento.setWeb(elemento.getElementsByTagName("web").item(0).getTextContent());
					} catch(NullPointerException e) {
						alojamiento.setWeb("https://Siglo20/no-21.com");
					}
					try {
						alojamiento.setMarks(elemento.getElementsByTagName("marks").item(0).getTextContent());
					} catch(NullPointerException e) {
						alojamiento.setMarks("Vacío");
					}
					try {
						alojamiento.setMunicipality(elemento.getElementsByTagName("municipality").item(0).getTextContent());
					} catch(NullPointerException e) {
						alojamiento.setMunicipality("Amorebieta");
					}
					try {
						String provincia = elemento.getElementsByTagName("territory").item(0).getTextContent();
						alojamiento.setProvincia(comprobarProvincia(provincias, provincia, leer));
						
					} catch(NullPointerException e) {
						alojamiento.setProvincia(provincias.get(0));
					}
					try {
						alojamiento.setLatwgs84(Float.parseFloat(elemento.getElementsByTagName("latwgs84").item(0).getTextContent()));
					} catch(NullPointerException e) {
						alojamiento.setLatwgs84(51.3901716f);
					}
					try {
						alojamiento.setLonwgs84(Float.parseFloat(elemento.getElementsByTagName("lonwgs84").item(0).getTextContent()));
					} catch(NullPointerException e) {
						alojamiento.setLonwgs84(30.1000834f);
					}
					try {
						alojamiento.setPostalcode(elemento.getElementsByTagName("postalcode").item(0).getTextContent());
					} catch(NullPointerException e) {
						alojamiento.setPostalcode("48340");
					}
					try {
						alojamiento.setCapacity(Integer.parseInt(elemento.getElementsByTagName("capacity").item(0).getTextContent()));
					} catch(NullPointerException e) {
						alojamiento.setCapacity(13);
					}
					try {
						alojamiento.setRestaurant(Integer.parseInt(elemento.getElementsByTagName("restaurant").item(0).getTextContent()));
					} catch(NullPointerException e) {
						alojamiento.setRestaurant(0);
					}
					try {
						alojamiento.setStore(Integer.parseInt(elemento.getElementsByTagName("store").item(0).getTextContent()));
					} catch(NullPointerException e) {
						alojamiento.setStore(0);
					}
					try {
						alojamiento.setAutocaravana(Integer.parseInt(elemento.getElementsByTagName("autocaravana").item(0).getTextContent()));
					} catch(NullPointerException e) {
						alojamiento.setAutocaravana(0);
					}
					alojamientos.add(alojamiento);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return alojamientos;
	}
	
	
	public Provincia comprobarProvincia(ArrayList<Provincia> provincias, String provincia, LeerFicheros leer) {
		provincia = Normalizer.normalize(provincia, Normalizer.Form.NFD);   
		provincia = (provincia.replaceAll("[^\\p{ASCII}]", "")).toLowerCase();
		if (provincia.contains("alava") || provincia.contains("araba")) {
			return provincias.get(1);
		} else if (provincia.contains("vizcaya") || provincia.contains("bizkaia")) {
			return provincias.get(0);
		} else if (provincia.contains("guipuzcoa") || provincia.contains("gipuzkoa"))  {
			return provincias.get(2);
		} else {
			return provincias.get(0);
		}
	}
}