package unrc.dose;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;


/** == Schema Info.
* Table name: comments
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
  *@param title the comment's title
  *@param description the comment's body
  *@param challengeId the challenge's id which is commented
  *@param userId this the user's id who commented
  *@return the comment created
  **/
  public static Comment createComment(final String title,
      final String description,
      final int challengeId,
      final int userId) {
    if (description == null || title == null
        || description == "" || title == "") {
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
    return c.getInteger("comment_id") != (null);
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
    } else {
      throw new IllegalArgumentException("Can't respond to a response comment");
    }
    return comment;
  }

  /**
  *create a list of comments associated to the id.
  *@param id the object's id
  *@param obj can be a User, Comment or Challenge
  *@return a list of comments
  */
  public static LazyList<Comment> viewComment(final int id, final Object obj) {
    LazyList<Comment> list = null;
    if (obj instanceof User) {
      list = Comment.where("user_id=?", id);
    } else if (obj instanceof Challenge) {
      list = Comment.where("challenge_id=?", id);
    } else if (obj instanceof Comment) {
      list = Comment.where("comment_id=?", id);
    } else {
      throw new IllegalArgumentException("invalid type");
    }
    return list;
  }

  /**
  *creation of equal method.
  *@param aux the object to compare
  *@return true if two comments are equals
  */
  public boolean equals(final Object aux) {
    return (this.getId().equals(((Comment) aux).getId()));
  }

  /**
   * toString of Comment.
   * @return comment as a string
   */
  public String toString() {
    return "[id: " + this.getId() + ", title: "
      + this.getString("title") + "description: "
      + this.getString("description") + "user_id: "
      + this.getInteger("user_id") + "challenge_id: "
      + this.getInteger("challenge_id") + "father_id:"
      + this.getInteger("comment_id") + "]";
  }

}
