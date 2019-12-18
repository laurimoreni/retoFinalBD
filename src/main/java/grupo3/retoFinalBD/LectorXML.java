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
		ArrayList<String> nombreProvincia = new ArrayList<String>();
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
					int posicion = 1;
					try {
						alojamiento.setSignatura(elemento.getElementsByTagName("signatura").item(0).getTextContent());
						posicion = 2;
						alojamiento.setDocumentname(elemento.getElementsByTagName("documentname").item(0).getTextContent());
						posicion = 3;
						alojamiento.setTurismdescription(elemento.getElementsByTagName("turismdescription").item(0).getTextContent());
						posicion = 4;
						alojamiento.setLodgingtype(elemento.getElementsByTagName("lodgingtype").item(0).getTextContent());
						posicion = 5;
						alojamiento.setAddress(elemento.getElementsByTagName("address").item(0).getTextContent());
						posicion = 6;
						alojamiento.setPhone(formato.formatoNumero(elemento.getElementsByTagName("phone").item(0).getTextContent()));
						posicion = 7;
						alojamiento.setTourismemail(elemento.getElementsByTagName("tourismemail").item(0).getTextContent());
						posicion = 8;
						alojamiento.setWeb(elemento.getElementsByTagName("web").item(0).getTextContent());
						posicion = 9;
						alojamiento.setMarks(elemento.getElementsByTagName("marks").item(0).getTextContent());
						posicion = 10;
						alojamiento.setMunicipality(elemento.getElementsByTagName("municipality").item(0).getTextContent());
						posicion = 11;
						if(!nombreProvincia.contains(elemento.getElementsByTagName("territory").item(0).getTextContent())) {
							provincias = CargarProvincias(elemento.getElementsByTagName("territory").item(0).getTextContent(), provincias, id);
							nombreProvincia.add(elemento.getElementsByTagName("territory").item(0).getTextContent());
							id++;
						} else {
							for(Provincia p: provincias) {
								if(p.getNombre().equals(elemento.getElementsByTagName("territory").item(0).getTextContent())) {
									alojamiento.setProvincia(p);
									break;
								}
							}
						}
						posicion = 12;
						alojamiento.setLatwgs84(Float.parseFloat(elemento.getElementsByTagName("latwgs84").item(0).getTextContent()));
						posicion = 13;
						alojamiento.setLonwgs84(Float.parseFloat(elemento.getElementsByTagName("lonwgs84").item(0).getTextContent()));
						posicion = 14;
						alojamiento.setPostalcode(elemento.getElementsByTagName("postalcode").item(0).getTextContent());
						posicion = 15;
						alojamiento.setCapacity(Integer.parseInt(elemento.getElementsByTagName("capacity").item(0).getTextContent()));
						posicion = 16;
						alojamiento.setRestaurant(Integer.parseInt(elemento.getElementsByTagName("restaurant").item(0).getTextContent()));
						posicion = 17;
						alojamiento.setStore(Integer.parseInt(elemento.getElementsByTagName("store").item(0).getTextContent()));
						posicion = 18;
						alojamiento.setAutocaravana(Integer.parseInt(elemento.getElementsByTagName("autocaravana").item(0).getTextContent()));
							
					} catch(NullPointerException e) {
						switch(posicion) {
							case 2: alojamiento.setDocumentname("Sin nombre");
								break;
							case 3: alojamiento.setTurismdescription("Sin descripcion");
								break;
							case 4: alojamiento.setLodgingtype("Algo rural");
								break;
							case 5: alojamiento.setAddress("Dirección");
								break;
							case 6: alojamiento.setPhone("555863147");
								break;
							case 7: alojamiento.setTourismemail("sin_tag@nosevalidarxml.com");
								break;
							case 8: alojamiento.setWeb("https:\\Siglo20\no-21.com");
								break;
							case 9: alojamiento.setMarks("Montes y Valles vascos");
								break;
							case 10: alojamiento.setMunicipality("Amorebieta");
								break;
							case 11: alojamiento.setProvincia(provincias.get(0));
								break;
							case 12: alojamiento.setLatwgs84(51.3901716f);
								break;
							case 13: alojamiento.setLonwgs84(30.1000834f);
								break;
							case 14: alojamiento.setPostalcode("48340");
								break;
							case 15: alojamiento.setCapacity(13);
								break;
							case 16: alojamiento.setRestaurant(0);
								break;
							case 17: alojamiento.setStore(0);
								break;
							case 18: alojamiento.setAutocaravana(0);
								break;
						}
					}
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