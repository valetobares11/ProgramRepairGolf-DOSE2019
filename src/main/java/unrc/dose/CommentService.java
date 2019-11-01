package unrc.dose;

import com.google.gson.Gson;

/**
* Class which calls comment's methods and transforms they to json.
*/
public final class CommentService {
  /**
  *Invokes the method which creates a comments and transforms to json.
  *@param title the comment's title
  *@param description the comment's body
  *@param challengeId the challenge's id which is commented
  *@param userId this the user's id who commented
  *@return a comment transformed to json
  **/
  public String comment(final String title,
      final String description,
      final int challengeId,
      final int userId) {
    Gson g = new Gson();
    return g.toJson(Comment.createComment(
      title, description, challengeId, userId));

  }

  /**
  *Invokes the method which creates a response and transforms to json.
  *@param description the response's body
  *@param userId this is the user's id who responded to a comment
  *@param commentId this is the id to the comment it references
  *@return a response transformed to json
  **/

  public String response(final String description,
      final int userId,
      final int commentId) {
    Gson g = new Gson();
    return g.toJson(Comment.createResponse(description, userId, commentId));

  }

  /**
  *Invokes the method which shows a list of comments associated to the id.
  *@param id the object's id
  *@param obj can be a User, Comment or Challenge
  *@return a comment transformed to json
  **/
  public String view(final int id, final Object obj) {
    Gson g = new Gson();
    return g.toJson(Comment.viewComment(id, obj).toArray());

  }

}
