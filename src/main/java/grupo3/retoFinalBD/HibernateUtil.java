package grupo3.retoFinalBD;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
 
    private static SessionFactory buildSessionFactory() {
        try {
        	File config = new File("src/main/resources/hibernate.cfg.xml");
            return new Configuration().configure(config).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Error al crear SessionFactory" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public static void shutdown() {
        getSessionFactory().close();
    }
}