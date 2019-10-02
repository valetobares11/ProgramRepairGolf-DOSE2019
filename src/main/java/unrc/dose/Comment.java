package unrc.dose;

import org.javalite.activejdbc.Model;

/*
* == Schema Info
*
* Table name: comments
*
* id              :integer    not null, primary key
* title           :varchar(50)    not null
* description     :varchar(300)    not null
* challenge_id    :integer     not null
* user_id         :integer    not null
* comment_id      :integer
**/
/**
*@author Celi Yanina, de Prada Hernan, Goldenberg Erika
**/
public class Comment extends Model {

  /**
  *Create a new comment.
  *@param title this is the comment's title
  *@param description this the comment's body
  *@param challenge_id this is the challenge's id which is commented
  *@param user_id this is the user's id who commented
  **/
  /**
  *Create a new Response.
  *@param description this the response's body
  *@param challenge_id this is the challenge's id which is commented
  *@param user_id this is the user's id who responded to a comment
  **/
  /**
  *isResponse
  *@param comment_id this is comments or response to evaluate
  *@return return if the selected comment_id si a comment or a response.
  **/
  public void createComment (String title,String description, int challenge_id, int user_id ){
    this.set("title",title);
    this.set("description",description);
    this.set("challenge_id", challenge_id);
    this.set("user_id", user_id);
    this.saveIt();
  }

  public boolean isResponse(int comment_id){
    Comment c = Comment.findById(comment_id);
    return ((c.getInteger("comment_id")) != (null));
  }

  public void createResponse (String description, int user_id, int comment_id) throws NullPointerException{
    if (!isResponse(comment_id)){
    Comment c = Comment.findById(comment_id);
    this.set("description", description);
    this.set("user_id", user_id);
    this.set("title", "Re :" + c.getString("title"));
    this.set("comment_id", comment_id);
    this.set("challenge_id", c.getInteger("challenge_id"));
    this.saveIt();
  }
  }
}
