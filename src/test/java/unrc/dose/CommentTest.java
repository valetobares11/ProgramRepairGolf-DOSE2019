package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import unrc.dose.Comment;
import unrc.dose.User;
import unrc.dose.Challenge;
import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



public class CommentTest {



	@BeforeClass
	public static void before() {
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/repair_game_test", "root", "root");
		System.out.println("CommentTest setup");
		Base.openTransaction();
	}

	@AfterClass
	public static void after() {
		System.out.println("CommentTest tearDown");
		Base.rollbackTransaction();
		Base.close();
	}

	@Test
	public void testEquals() {
		User u = new User();
		u.set("username", "juan");
		u.set("password", "root");
		u.set("email_address", "juanpablo@gmail.com");
		u.set("admin", 0);
		u.saveIt();
		User p = new User();
		p.set("username", "juana");
		p.set("password", "root");
		p.set("email_address", "juana@gmail.com");
		p.set("admin", 1);
		p.saveIt();
		Challenge ch = new Challenge ();
		ch.set("title", "Test" );
		ch.set("description", "descripcion de prueba");
		ch.set("source", "codigo");
		ch.set("point", 20);
		ch.set("owner_id", p.getInteger("id"));
		ch.set("owner_solution_id", u.getInteger("id"));
		ch.saveIt();
		Comment c = new Comment();
		c.set("title", "Titulo");
		c.set("description", "Descripcion");
		c.set("challenge_id", ch.getInteger("id"));
		c.set("user_id", u.getInteger("id"));
		c.saveIt();
		Comment aux = Comment.findById(c.getInteger("id"));
		assertTrue(Comment.equalsComment(c,aux));

	}
	@Test
	public void loadComment() {
		User u = new User();
		u.set("username", "juan");
		u.set("password", "root");
		u.set("email_address", "juanpablo@gmail.com");
		u.set("admin", 0);
		u.saveIt();
		User p = new User();
		p.set("username", "juana");
		p.set("password", "root");
		p.set("email_address", "juana@gmail.com");
		p.set("admin", 1);
		p.saveIt();
		Challenge ch = new Challenge ();
		ch.set("title", "Test" );
		ch.set("description", "descripcion de prueba");
		ch.set("source", "codigo");
		ch.set("point", 20);
		ch.set("owner_id", p.getInteger("id"));
		ch.set("owner_solution_id", u.getInteger("id"));
		ch.saveIt();
		Comment c = Comment.createComment("Holi","Esto es un Comentario", ch.getInteger("id"), u.getInteger("id"));
		Comment aux = Comment.findById(c.getInteger("id"));
		assertNotNull(aux);
		}

		@Test
		public void loadResponse() {
			User u = new User();
			u.set("username", "juan");
			u.set("password", "root");
			u.set("email_address", "juanpablo@gmail.com");
			u.set("admin", 0);
			u.saveIt();
			User p = new User();
			p.set("username", "juana");
			p.set("password", "root");
			p.set("email_address", "juana@gmail.com");
			p.set("admin", 1);
			p.saveIt();
			Challenge ch = new Challenge ();
			ch.set("title", "Test" );
			ch.set("description", "descripcion de prueba");
			ch.set("source", "codigo");
			ch.set("point", 20);
			ch.set("owner_id", p.getInteger("id"));
			ch.set("owner_solution_id", u.getInteger("id"));
			ch.saveIt();
			Comment c = new Comment();
			c.set("title", "Titulo");
			c.set("description", "Descripcion");
			c.set("challenge_id", ch.getInteger("id"));
			c.set("user_id", u.getInteger("id"));
			c.saveIt();
			Comment rta = Comment.createResponse("respuesta prueba",p.getInteger("id"),c.getInteger("id"));
			Comment aux = Comment.findById(rta.getInteger("id"));
			assertNotNull(aux);
			}


}
