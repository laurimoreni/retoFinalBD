package grupo3.retoFinalBD;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLHandler extends DefaultHandler {

//	private String valor = null;    
//    private Libro libro;
//    FuncionesMain funcionesMain = new FuncionesMain();
//	   private ArrayList<Libro> libros = new ArrayList<Libro>(); 
//	   
//	   public ArrayList<Libro> librosXML() {  
//	      return libros;
//	   }  
//	  
//   @Override
//   public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {  
//	
//	 valor = null;  
//         
//	  if(name.equalsIgnoreCase("libro")){  
//	     libro = new Libro();
//	  }
//   }
//     
//   @Override  
//   public void characters(char[] ch, int start, int length)  
//         throws SAXException {
//      
//      valor = new String(ch,start,length);  
//   }
//  
//   @Override  
//   public void endElement(String uri, String localName, String name) throws SAXException {  
//      
//	   switch (name.toLowerCase()) {
//	   	  case "autor": libro.setAutor(valor);break;
//	   	  case "titulo": libro.setTitulo(valor); break;
//	   	  case "editorial": libro.setEditorial(valor);break;
//	   	  case "paginas":
//	   		  if (valor != null) {
//	   			  libro.setPaginas(Integer.parseInt(valor));
//	   		  }
//	   		  break;
//	   	  case "altura":
//			  if (valor != null) {
//			 	  libro.setAltura(Integer.parseInt(valor));
//			  }
//			  break;
//	   	  case "notas": libro.setNotas(valor);break;
//	   	  case "isbn": libro.setIsbn(valor);break;
//	   	  case "materias": libro.setMaterias(valor);break;
//	   	  case "libro": 
//	   		  libros.add(libro);break;
//	   }        
//   }
}
