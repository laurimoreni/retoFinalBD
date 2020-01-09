package grupo3.retoFinalBD;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
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
					try {
						String zipFileUrl = elemento.getElementsByTagName("zipfile").item(0).getTextContent();
						String[] partesRuta = zipFileUrl.split("/"); 
						leer.descargarFichero(new URL(zipFileUrl), partesRuta[partesRuta.length - 1]);
						if (archivo.getPath().contains("alojamientos")) {
							extraerImagenAloj(partesRuta[partesRuta.length - 1]);
						} else {
							extraerImagen(partesRuta[partesRuta.length - 1]);
						}
						Image img = ImageIO.read(new File("imagen.jpg"));
						alojamiento.setImagen(imagenToBlob(img));
						File ficheroTemp = new File("ficheros/Temp/" + partesRuta[partesRuta.length - 1]);
						if (ficheroTemp.exists()) {
							FileUtils.forceDelete(ficheroTemp);
						}
						File imagenTemp = new File("ficheros/Temp/imagen.jpg");
						if (ficheroTemp.exists()) {
							FileUtils.forceDelete(imagenTemp);
						}
					} catch(NullPointerException e) {
						alojamiento.setImagen(imagenToBlob(ImageIO.read(new File("imagen.jpg"))));
					} catch (Exception ex) {
						ex.printStackTrace();
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
	
	public void extraerImagen(String fichero) {
		String directorioZip = "ficheros/Temp/";
		ZipInputStream zis = null;
		FileOutputStream fos = null;
		
		try {
			//crea un buffer temporal para el archivo que se va descomprimir
			zis = new ZipInputStream(new FileInputStream(directorioZip + fichero));
	
			ZipEntry salida;
			//recorre todo el buffer extrayendo uno a uno cada archivo.zip y creándolos de nuevo en su archivo original 
			while ((salida = zis.getNextEntry()) != null) {
				String[] carpetas = salida.getName().split("/");
				
				if (carpetas[1].substring(0, 2).equals("es") && carpetas[2].equals("images")) {
					if (carpetas.length == 4) {
						if (carpetas[3].substring(0,2).equals("FP")) {
							fos = new FileOutputStream(directorioZip + "imagen.jpg");
							int leer;
							byte[] buffer = new byte[1024];
							while (0 < (leer = zis.read(buffer))) {
								fos.write(buffer, 0, leer);
							}
						}
					}
					
				}				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (zis != null) {
				try {
					zis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void extraerImagenAloj(String fichero) {
		String directorioZip = "ficheros/Temp/";
		ZipInputStream zis = null;
		FileOutputStream fos = null;
		boolean getImage = false;
		
		try {
			//crea un buffer temporal para el archivo que se va descomprimir
			zis = new ZipInputStream(new FileInputStream(directorioZip + fichero));
	
			ZipEntry salida;
			//recorre todo el buffer extrayendo uno a uno cada archivo.zip y creándolos de nuevo en su archivo original 
			while ((salida = zis.getNextEntry()) != null && !getImage) {
				String[] carpetas = salida.getName().split("/");
				
				if (carpetas[1].substring(0, 2).equals("es") && carpetas[2].equals("images")) {
					if (carpetas.length == 4) {
						if (salida.getSize() > 100000) {
							fos = new FileOutputStream(directorioZip + "imagen.jpg");
							int leer;
							byte[] buffer = new byte[1024];
							while (0 < (leer = zis.read(buffer))) {
								fos.write(buffer, 0, leer);
							}
							
							getImage = true;
						}
					}
					
				}				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} if (zis != null) {
				try {
					zis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Blob imagenToBlob ( Image imagen ) {

		Blob imagenBlob = null;
		BufferedImage bi = new BufferedImage ( imagen.getWidth ( null ), imagen.getHeight ( null ), BufferedImage.TYPE_INT_ARGB );
		Graphics bg = bi.getGraphics ();
		bg.drawImage ( imagen, 0, 0, null );
		bg.dispose ();

		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		try {
			ImageIO.write (bi, "jpg", baos );
			baos.flush ();
			baos.close ();
		} catch ( IOException e ) {
			e.printStackTrace ();
		}

		byte [] imagenByte = baos.toByteArray ();

		try {
			imagenBlob = new SerialBlob ( imagenByte );
		} catch ( SerialException se ) {
			se.printStackTrace ();
		} catch ( SQLException sqle ) {
			sqle.printStackTrace ();
		}
		return imagenBlob;
	}
}