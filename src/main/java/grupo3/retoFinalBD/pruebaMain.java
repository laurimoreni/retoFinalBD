package grupo3.retoFinalBD;

import java.util.ArrayList;

import org.hibernate.Session;

public class pruebaMain {

	public static void main(String[] args) {
		
		// iniciar sesion hibernate
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		// leer ficheros XML
		LeerFicheros leer = new LeerFicheros();
		ArrayList<Alojamiento> alojamientos = new ArrayList<Alojamiento>();
		alojamientos = leer.leerFicheroXML(alojamientos);
		
		// guardar datos en BD
		for(Provincia provincia: leer.provincias) {
			session.save(provincia);
		}
		
		for (Alojamiento alojamiento : alojamientos) {
			session.save(alojamiento);
		}
		session.getTransaction().commit();
		
		// leer datos de la BD
		// LecturaBD lectur = new LecturaBD();
		
		// escribir los datos en archivo JSON
		LectorJSON json = new LectorJSON();
		json.convertirAJson(alojamientos);

		// cerrar sesion hibernate
		HibernateUtil.shutdown();
		
	}

}
