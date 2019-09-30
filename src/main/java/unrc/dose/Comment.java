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
**/
package unrc.dose;

import org.javalite.activejdbc.Model;

public class Comment extends Model {
}
