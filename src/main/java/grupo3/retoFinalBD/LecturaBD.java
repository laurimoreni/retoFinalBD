package grupo3.retoFinalBD;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class LecturaBD {
	
	public ArrayList<Alojamiento> getAlojamientos() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Query<Alojamiento> query = session.createQuery("from Alojamiento", Alojamiento.class);
		ArrayList<Alojamiento> alojamientos = (ArrayList<Alojamiento>) query.getResultList();
		
		return alojamientos;
	}

}
