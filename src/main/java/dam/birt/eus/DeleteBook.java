package dam.birt.eus;

import dam.birt.eus.entidades.Author;
import dam.birt.eus.entidades.Book;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DeleteBook {

	public static void main(String[] args) {

		// crea sessionFactory y session
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
			    .configure( "hibernate.cfg.xml" )
			    .build();

		Metadata metadata = new MetadataSources( standardRegistry )
			    .addAnnotatedClass( Author.class )
			    .addAnnotatedClass( Book.class )
			    .getMetadataBuilder()
			    .build();

		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder()
			    .build();    
		
		Session session = sessionFactory.openSession();
		
		try {			

			System.out.println("Borrando un libro sin borrar su autor");
			
			int book_id = 1;
			
			Book tempBook = session.get(Book.class, book_id);

			// comienza la transacción
			session.beginTransaction();
		
			// borra el libro pero no el autor.
			session.remove(tempBook);
			
			// hace commit de la transaccion
			session.getTransaction().commit();
					
			System.out.println("Libro borrado!");
		}
		catch ( Exception e ) {
			// rollback ante alguna excepci n
			System.out.println("Realizando Rollback");
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
			sessionFactory.close();
		}
	}
	
}