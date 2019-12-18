package grupo3.retoFinalBD;

import java.net.URL;
import java.util.ArrayList;

public class pruebaMain {

	public static void main(String[] args) {
		LeerFicheros ficheros = new LeerFicheros();
		
		ArrayList<Alojamiento> alojamientos = new ArrayList<Alojamiento>();
		
		alojamientos = ficheros.leerFicheroXML(alojamientos);
		
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		session.beginTransaction();
//		// Añadir un nuevo objeto alojamiento
//		Alojamiento alojamiento = new Alojamiento();
//		alojamiento.setSignatura("aaaaa");
//		alojamiento.setDocumentname("demo");
//		alojamiento.setCapacity(13);
//		session.save(alojamiento);
//		session.getTransaction().commit();
//		HibernateUtil.shutdown();
	}

}
