package grupo3.retoFinalBD;

import java.net.URL;
import java.util.ArrayList;

import org.hibernate.Session;

public class pruebaMain {

	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		// Añadir un nuevo objeto alojamiento
		Alojamiento alojamiento = new Alojamiento();
		alojamiento.setSignatura("aaaaa");
		alojamiento.setDocumentname("demo");
		alojamiento.setCapacity(13);
		session.save(alojamiento);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
		
		LecturaBD lectur = new LecturaBD();
		ArrayList<Alojamiento> aloja = lectur.getAlojamientos();
		LectorJSON json = new LectorJSON();
		json.convertirAJson(aloja);
	}

}
