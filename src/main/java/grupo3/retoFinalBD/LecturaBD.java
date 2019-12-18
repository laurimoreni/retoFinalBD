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
		HibernateUtil.shutdown();
		return alojamientos;
	}
	
	public ArrayList<Provincia> getProvincias() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query<Provincia> query = session.createQuery("from Provincia", Provincia.class);
		ArrayList<Provincia> provincias = (ArrayList<Provincia>) query.getResultList();
		HibernateUtil.shutdown();
		return provincias;
	}
	
	public ArrayList<Reserva> getReservas() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query<Reserva> query = session.createQuery("from Reserva", Reserva.class);
		ArrayList<Reserva> reservas = (ArrayList<Reserva>) query.getResultList();
		HibernateUtil.shutdown();
		return reservas;
	}
	
	public ArrayList<Usuario> getUsuarios() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Query<Usuario> query = session.createQuery("from Usuario", Usuario.class);
		ArrayList<Usuario> usuarios = (ArrayList<Usuario>) query.getResultList();
		HibernateUtil.shutdown();
		return usuarios;
	}

}
