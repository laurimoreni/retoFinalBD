package grupo3.retoFinalBD;

import java.net.URL;
import java.util.ArrayList;

import org.hibernate.Session;

public class pruebaMain {

	public static void main(String[] args) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		// Añadir un nuevo objeto alojamiento
		LeerFicheros leer = new LeerFicheros();
		ArrayList<Alojamiento> alojamientos = new ArrayList<Alojamiento>();
		alojamientos = leer.leerFicheroXML(alojamientos);
		for(Alojamiento alojamiento: alojamientos) {
			session.save(alojamiento);
		}
		session.getTransaction().commit();
		
		LecturaBD lectur = new LecturaBD();
		ArrayList<Alojamiento> aloja = lectur.getAlojamientos();
		LectorJSON json = new LectorJSON();
		json.convertirAJson(aloja);
		HibernateUtil.shutdown();
	}

}
