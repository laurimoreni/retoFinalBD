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

	public ArrayList<Alojamiento> CargarAlojamientos(File archivo, ArrayList<Alojamiento> alojamientos, LeerFicheros leer){
		Formatos formato = new Formatos();
		ArrayList<Provincia> provincias = new ArrayList<Provincia>();
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
						alojamiento.setWeb("https:\\Siglo20\no-21.com");
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
						if(!leer.nombreProvincia.contains(elemento.getElementsByTagName("territory").item(0).getTextContent())) {
							if(!elemento.getElementsByTagName("territory").item(0).getTextContent().contains(" ")) {
								provincias = CargarProvincias(elemento.getElementsByTagName("territory").item(0).getTextContent(), provincias, provincias.size() + 1, leer);
								leer.nombreProvincia.add(elemento.getElementsByTagName("territory").item(0).getTextContent());
							}
						} else {
							for(Provincia p: provincias) {
								if(p.getNombre().equals(elemento.getElementsByTagName("territory").item(0).getTextContent())) {
									alojamiento.setProvincia(p);
									break;
								}
							}
						}
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
					System.out.println(elemento.getElementsByTagName("signatura").item(0).getTextContent());
					alojamientos.add(alojamiento);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return alojamientos;
	}
	
	public ArrayList<Provincia> CargarProvincias(String nombre, ArrayList<Provincia> provincias, int id, LeerFicheros leer){
		Provincia provincia = new Provincia();
		
		provincia.setNombre(nombre);
		provincia.setId(id);
		provincias.add(provincia);
		leer.provincias.add(provincia);
		return provincias;
	}
}