package grupo3.retoFinalBD;

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
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.apache.commons.codec.binary.Base64;

public class LectorXML {

	public ArrayList<Alojamiento> cargarAlojamientos(File archivo, ArrayList<Alojamiento> alojamientos, ArrayList<Provincia> provincias, LeerFicheros leer, Session session){
		Formatos formato = new Formatos();
		Logger logger = Logger.getSingletonInstance();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY - hh:mm:ss");
		
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
						String signatura = elemento.getElementsByTagName("signatura").item(0).getTextContent();
						if (signatura != "" && signatura!= null) {
							alojamiento.setSignatura(signatura);
							try {
								alojamiento.setDocumentname(elemento.getElementsByTagName("documentname").item(0).getTextContent());
							} catch(NullPointerException e) {
								alojamiento.setDocumentname("Sin nombre");
							}
							try {
								alojamiento.setTurismdescription(elemento.getElementsByTagName("turismdescription").item(1).getTextContent());
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
								// Descargar ZIP del alojamiento
								leer.descargarFichero(new URL(zipFileUrl), partesRuta[partesRuta.length - 1]);
								// Extraer imagenes del alojamiento
								extraerImagenes(partesRuta[partesRuta.length - 1]);
								//seleccionar la imagen mas grande
								File ficheroImagen = seleccionarImagen();
								Image img;
								if (ficheroImagen.exists()) {
									img = ImageIO.read(ficheroImagen);
								} else {
									img = ImageIO.read(new File("imagen.jpg"));
								}
								alojamiento.setImagen(imagenToBlob(img));
								// Borrar archivos de imagen temporales
								borrarArchivos();			
							} catch(NullPointerException e) {
								alojamiento.setImagen(imagenToBlob(ImageIO.read(new File("imagen.jpg"))));
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							
							alojamiento.setActivo(1);
							alojamientos.add(alojamiento);
						}
					} catch (Exception e) {
						logger.escribirLog(dateFormat.format(new Date()) + " - " + getClass().getName() + " - Alojamiento sin campo signatura.");
					}
					
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
	
	public void extraerImagenes(String fichero) {
		String directorioZip = "ficheros/Temp/";
		ZipInputStream zis = null;
		FileOutputStream fos = null;
		boolean getImages = false;
		int cont = 0;
		
		try {
			//crea un buffer temporal para el archivo que se va descomprimir
			zis = new ZipInputStream(new FileInputStream(directorioZip + fichero));
	
			ZipEntry salida;
			//recorre todo el buffer extrayendo uno a uno cada archivo.zip y creándolos de nuevo en su archivo original 
			while ((salida = zis.getNextEntry()) != null && !getImages) {
				while (salida.getName().contains("/images/") && !getImages) {
					salida = zis.getNextEntry();
					String[] carpetas = salida.getName().split("/");
					if (carpetas.length == 4) {													//estamos en un archivo dentro de la carpeta images
						fos = new FileOutputStream(directorioZip + carpetas[3]);
						int leer;
						byte[] buffer = new byte[1024];
						while (0 < (leer = zis.read(buffer))) {
							fos.write(buffer, 0, leer);
						}
						if (fos != null) {
							try {
								fos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						cont ++;
					} else {
						if (cont > 0) {
							getImages = true;
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
	
	public File seleccionarImagen() {
		File imagen = null;
		long tamano = 0;
		long tamanoTemp = 0;
		
		File[] imagenes = new File("ficheros/Temp/").listFiles();
		
		for (File temp : imagenes) {
			tamanoTemp  = FileUtils.sizeOf(temp);
			if (tamanoTemp > tamano && FilenameUtils.getExtension(temp.getName()).equals("jpg")) {
				imagen = temp;
				tamano = tamanoTemp;
			}
		}
		
		return imagen;
	}
	
	public void borrarArchivos() {
		File[] imagenes = new File("ficheros/Temp/").listFiles();
		
		for (File temp : imagenes) {
			if (temp.exists()) {
				try {
					FileUtils.forceDelete(temp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Blob imagenToBlob(Image imagen) throws IOException{
		Blob imagenBlob = null;
		ByteArrayOutputStream baos=new ByteArrayOutputStream(1000);
		BufferedImage img=(BufferedImage) imagen;
		ImageIO.write(img, "jpg", baos);
		baos.flush();
 
		String base64String = Base64.encodeBase64String(baos.toByteArray());
		baos.close();

		byte[] imagenByte = Base64.decodeBase64(base64String);
		
		try {
			imagenBlob = new SerialBlob ( imagenByte );
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
 
		return imagenBlob;
	}
}