package dam.birt.eus;

import dam.birt.eus.entidades.Author;
import dam.birt.eus.entidades.Book;

import java.time.LocalDate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class CreateAuthor {
    
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

            // comienza la transacción
			session.beginTransaction();

			// crea un objeto Author
			System.out.println("Creando nuevo objeto Author");
		
			Author author = createAuthor();
								
			// guarda el objeto Author
			System.out.println("Guardando el autor");
			session.persist(author);
			
			// Commit de la transacción
			session.getTransaction().commit();
						
			System.out.println("Autor creado correctamente!");

		} catch ( Exception e ) {
			// rollback ante alguna excepción
			System.out.println("Realizando Rollback");
			session.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			session.close();
			sessionFactory.close();
		}
	}

	private static Author createAuthor() {
		Author tempAuthor = new Author();
		
		tempAuthor.setFirstName("Luka");
		tempAuthor.setLastName("Bergaretxe");
		tempAuthor.setBirthdate(LocalDate.parse("1993-04-03"));
		tempAuthor.setNationality("Spanish");
		return tempAuthor;		
	}
}
