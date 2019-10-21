package unrc.dose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.util.Arrays;
import unrc.dose.Password;
import org.junit.Test;


public class PasswordTest {

    /**
    * proof that the password that is passed when a user is created it's the same that is using to log in.
    * @result true, because expected password it's the same that it's passed now.
    */
    @Test
    public void correctPass() {
        String pass = "pepe";
        byte[] salt = Password.getNextSalt();
        byte[] hash = Password.hash(pass.toCharArray(), salt);

        assertEquals(true, Password.isExpectedPassword(pass.toCharArray(), salt, hash));
    }

    /**
    * proof that the password that is passed when a user is created it's different that is using to log in.
    * @result false, because expected password it's different that it's passed now.
    */
    @Test
    public void incorrectPass() {
        String pass = "juan";
        byte[] salt = Password.getNextSalt();
        byte[] hash = Password.hash(pass.toCharArray(), salt);
        String password = "jose";


        assertEquals(false, Password.isExpectedPassword(password.toCharArray(), salt, hash));
    }
}
