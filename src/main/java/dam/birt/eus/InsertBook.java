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

public class InsertBook {
    
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

			// Selecciona el Author existente y crea el Book
			System.out.println("Creando nuevo objeto Book");
		
            Author author = session.get(Author.class, 1);
			Book book = createBook();
						
			author.getBooks().add(book);
			book.setAuthor(author); //asociación bidireccional para mantener la coherencia en ambos lados
								
			// guarda el objeto Book
			System.out.println("Guardando el libro");
			session.persist(book);
			
			// Commit de la transacción
			session.getTransaction().commit();
						
			System.out.println("Libro creado correctamente!");

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

	private static Book createBook() {
		Book tempBook = new Book();
		
		tempBook.setTitle("Asfd");
		tempBook.setPublicationDate(LocalDate.parse("1993-04-03"));
		tempBook.setGenre("Horror");
		return tempBook;		
	}
}