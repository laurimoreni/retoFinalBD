package grupo3.retoFinalBD;

import org.hibernate.Session;

public class pruebaMain {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		// Añadir un nuevo objeto alumno
		PruebaEntity p = new PruebaEntity();
		p.setId(1);
		p.setFirstName("demo");
		p.setLastName("user");
		session.save(p);
		session.getTransaction().commit();
		HibernateUtil.shutdown();

	}

}
