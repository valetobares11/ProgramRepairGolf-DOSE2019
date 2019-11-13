package unrc.dose;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;

/** Endpoint wrapping everything related to Users. */
public final class UserEndpoint implements Endpoint {
    /** logger... */
    static final Logger LOGGER = LoggerFactory.getLogger(UserEndpoint.class);

    /** main name-space of this endpoint. */
    private static final String NAME_SPACE = "/user";

    /** service used to manipulate in memory the users. */
    //private static User user = new User();

    @Override
    public void bind(final SparkSwagger restApi) {

        restApi.endpoint(
            endpointPath(NAME_SPACE)
                .withDescription(
                    "user REST API exposing all users utilities"
                ), 
            (q, a) -> LOGGER.info("Logging Received request for User Rest API")
        )
        .get(
            path("/users")
                .withDescription("Will return all users in the store")
                .withResponseType(String.class),
            (req, res) -> {
            	//User user;
                return new Gson().toJson(User.findAll());
            }
        )

        .post(
        	path("/signUp/:username/:password/:email_address")
                .withDescription("Will return a boolean that describe if user has been created succesfully or not")
                .withPathParam()
                    .withName("username") 
                    .withDescription("The Username is the name of the user in the system")
                    .withName("password") 
                    .withDescription("The password is the key to log in the system")
                    .withName("email_address") 
                    .withDescription("The email_address is the form to contact with the user")
                .and()
                .withResponseType(String.class),
            (req, res) -> {
               	if (User.userExistsByUsername(req.params("username")) || User.userExistsByEmail(req.params("email_address"))) {
               		return "Nombre de usuario o email ya utilizados";
               	} else {
               		User user = User.set(req.params("username"), req.params("password"), req.params("email_address"), false);
               		
               		return"username: "+ (String)user.get("username")+ (String)user.get("email_address");
               	}

            }
        )
           

        .put(
            path("/resetPassword/:email_address")
                .withDescription("Send a new pass")
                .withPathParam()
                    .withName("email_address")
                    .withDescription("This param is for reset pass")
                    .and()
                .withResponseType(String.class),
            (req, res) -> {
                return(User.resetPassword(req.params("email_address")));
            }
        )
		
        .put(
            path("/updatePassword/:email_address/:oldPassword/:newPassword")
                .withDescription("Update a user's password")
                .withPathParam()
                    .withName("email_address")
                    .withDescription("This is the email param of user for update his pass")
                    .withName("oldPassword")
                    .withDescription("This is the password saved in database of user for update his pass, used as a security measure")
                    .withName("newPassword")
                    .withDescription("This is the new pass for update his pass, used as a security measure")
                .and()
                .withResponseType(Boolean.class),
            (req, res) -> {
            	return(User.updatePassword(req.params("email_address"), req.params("oldPassword"), req.params("newPassword")));
            }
        )
		

        .put(
            path("/updateUsername/:newEmail_address/:username")
                .withDescription("Update email of user data")
                .withPathParam()
                    .withName("newEmail_address")
                    .withDescription("This is the email for to be changed")
                    .withName("username")
                    .withDescription("This is the username for search user")
                    .and()
                .withResponseType(Boolean.class),
            (req, res) -> {
            	return(User.updateEmail(req.params("newEmail_address"), req.params("username")));            
            }
        )
		
        .post(
            path("/login/:username/:password")
                .withDescription("Verify user data for log in the system")
                .withPathParam()
                    .withName("username")
                    .withDescription("This is the user trying to log in")
                    .withName("password")
                    .withDescription("This is pass with which will user try log in")
                    .and()
                .withResponseType(Boolean.class),
            (req, res) -> {
                    return( User.validateCredentials(req.params("username"),req.params("password")));
            }
        )
	
        
        .put(
            path("/disableAccount/:username/:password")
                .withDescription("Disable logically user account")
                .withPathParam()
                    .withName("username")
                    .withDescription("This is the user to disable")
                    .withName("password")
                    .withDescription("This is pass with which will user disable account")
                    .and()
                .withResponseType(Boolean.class),
            (req, res) -> {
                return(User.disableUser(req.params("username"), req.params("password")));
                   	
            }
        );
    }
}
