package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import unrc.dose.Comment;
import unrc.dose.User;
import unrc.dose.Challenge;
import org.javalite.activejdbc.Base;
import org.junit.Before;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



public class CommentTest {

  private static User user;
	private static User admin;
	private static Challenge ch;



	@BeforeClass
	public static void beforeClass() {
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/repair_game_test", "root", "root");
		Base.openTransaction();
		user = User.set("Pepe", "root", "pepe@gmail.com", false);
	  admin = User.set("Juana", "root", "juana@gmail.com", true);
	  ch = new Challenge ();
	  ch.set("title", "Test" );
	  ch.set("description", "descripcion de prueba");
	  ch.set("source", "codigo");
	  ch.set("point", 20);
	  ch.set("owner_id", admin.getInteger("id"));
	  ch.set("owner_solution_id", user.getInteger("id"));
	  ch.saveIt();
	}

	@AfterClass
	public static void afterClass() {
		System.out.println("CommentTest tearDown");
		Base.rollbackTransaction();
		Base.close();
	}

	@Test
	public void testEquals() {
		Comment c = new Comment();
		c.set("title", "Titulo");
		c.set("description", "Descripcion");
		c.set("challenge_id", ch.getInteger("id"));
		c.set("user_id", user.getInteger("id"));
		c.saveIt();
		Comment aux = Comment.findById(c.getInteger("id"));
		assertTrue(Comment.equalsComment(c,aux));
  }

	@Test
	public void loadComment() {
		Comment c = Comment.createComment("Holi","Esto es un Comentario", ch.getInteger("id"), user.getInteger("id"));
		Comment aux = Comment.findById(c.getInteger("id"));
		assertNotNull(aux);
	}

	@Test
	public void loadResponse() {
		Comment c = new Comment();
		c.set("title", "Titulo");
		c.set("description", "Descripcion");
		c.set("challenge_id", ch.getInteger("id"));
		c.set("user_id", user.getInteger("id"));
		c.saveIt();
		Comment rta = Comment.createResponse("respuesta prueba",admin.getInteger("id"),c.getInteger("id"));
		Comment aux = Comment.findById(rta.getInteger("id"));
		assertNotNull(aux);
	}

}
