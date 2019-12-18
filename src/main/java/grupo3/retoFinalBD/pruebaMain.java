package grupo3.retoFinalBD;

import java.util.ArrayList;

import org.hibernate.Session;

public class pruebaMain {

	public static void main(String[] args) {
		
		// iniciar sesion hibernate
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		// Crear objetos alojamiento a la BD - sustituir con lector XML
		Alojamiento alojamiento1 = new Alojamiento();
		alojamiento1.setSignatura("aaaaa");
		alojamiento1.setDocumentname("demo");
		alojamiento1.setCapacity(13);
		Alojamiento alojamiento2 = new Alojamiento();
		alojamiento2.setSignatura("bbbbb");
		alojamiento2.setDocumentname("demo");
		alojamiento2.setCapacity(13);
		
		// crear ArrayList - sustituir con lector XML
		ArrayList<Alojamiento> alojamientos = new ArrayList<Alojamiento>();
		alojamientos.add(alojamiento1);
		alojamientos.add(alojamiento2);
		
		// guardar datos en BD
		for (Alojamiento alojamiento : alojamientos) {
			session.save(alojamiento);
		}
		session.getTransaction().commit();
		
		// leer datos de la BD
		LecturaBD lectur = new LecturaBD();
		ArrayList<Alojamiento> aloja = lectur.getAlojamientos();
		
		// escribir los datos en archivo JSON
		LectorJSON json = new LectorJSON();
		json.convertirAJson(aloja);

		// cerrar sesion hibernate
		HibernateUtil.shutdown();
	}

}
