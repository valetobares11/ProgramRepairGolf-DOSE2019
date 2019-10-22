package unrc.dose;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.LazyList;


/** == Schema Info.
*
* Table name: comments
*
* id              :integer    not null, primary key
* title           :varchar(50)    not null
* description     :varchar(300)    not null
* challenge_id    :integer     not null
* user_id         :integer    not null
* comment_id      :integer
*/
public class Comment extends Model {

  /**
  *Create a new comment.
  *@param title this is the comment's title
  *@param description this the comment's body
  *@param challengeId this is the challenge's id which is commented
  *@param userId this is the user's id who commented
  *@return the comment created
  **/
  public static Comment createComment(final String title,
  final String description, final int challengeId, final int userId) {
    if (description == null || title == null) {
    throw new IllegalArgumentException("Comment and title can't be empty");
    }
    Comment c = new Comment();
    c.set("title", title);
    c.set("description", description);
    c.set("challenge_id", challengeId);
    c.set("user_id", userId);
    c.saveIt();
    return c;
  }

  /**
  *isResponse.
  *@param commentId this is comments or response to evaluate
  *@return return if the selected comment_id si a comment or a response.
  **/
  public static boolean isResponse(final int commentId) {
    Comment c = Comment.findById(commentId);
    return ((c.getInteger("comment_id")) != (null));
  }

  /**
  *Create a new Response.
  *@param description this the response's body
  *@param userId this is the user's id who responded to a comment
  *@param commentId this is the id to the comment it references
  *@return the created response
  *@throws NullPointerException when doesn't exits the comment which this id
  **/
  public static Comment createResponse(final String description,
  final int userId, final int commentId) {
    if (description == null) {
    throw new IllegalArgumentException("The response can't be empty");
  }
    Comment comment = new Comment();
    if (!isResponse(commentId)) {
      Comment c = Comment.findById(commentId);
      comment.set("description", description);
      comment.set("user_id", userId);
      comment.set("title", "Re :" + c.getString("title"));
      comment.set("comment_id", commentId);
      comment.set("challenge_id", c.getInteger("challenge_id"));
      comment.saveIt();
    }
    return comment;
  }

  /**
  *create a list of comments.
  *@param id the object's id
  *@param obj the object which the id is searched
  *@return a list of comments
  */
  public static LazyList<Comment> viewComment(final int id, final Object obj) {
    LazyList<Comment> lista = null;
    if (obj instanceof User) {
      lista = Comment.where("user_id=?", id);
    } else if (obj instanceof Challenge) {
     lista = Comment.where("challenge_id=?", id);
    } else if (obj instanceof Comment) {
      lista = Comment.where("comment_id=?", id);
    }
    return lista;
  }

  /**
  *creation of equal method.
  *@param c a comment
  *@param aux another comment
  *@return true if two comments are equals
  */
  public static boolean equalsComment(final Comment c, final Comment aux) {
    return (aux.getInteger("id").equals(c.getInteger("id")));
  }

}
