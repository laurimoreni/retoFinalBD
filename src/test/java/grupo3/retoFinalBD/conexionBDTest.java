package grupo3.retoFinalBD;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.Test;

public class conexionBDTest {

	@Test
	public void testConexion() {
		boolean resultado = false;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
		} catch (Exception ex) {
			
		} finally {
			if (session != null) {
				resultado = true;
			}
		}
		assertEquals(true, resultado);
	}
	
	@Test
	public void lecturaBDText() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Usuario user = new Usuario("11111111A", "prueba", "prueba", "prueba@prueba.com", "dc468c70fb574ebd07287b38d0d0676d", "666666666", 1, "inactivo");
		
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		
		Usuario userBD = session.get(Usuario.class, "11111111A");
		
		assertEquals(user, userBD);
		
		userBD.setActivo("activo");
		
		Usuario userBDactualizado = session.get(Usuario.class,  "11111111A");
		
		assertEquals(userBD, userBDactualizado);
		
		session.beginTransaction();
		session.delete(userBD);
		session.getTransaction().commit();
		Usuario userBorrado = session.get(Usuario.class, "11111111A");
		
		assertEquals(userBorrado, null);
		
		HibernateUtil.shutdown();
		
	}

}
