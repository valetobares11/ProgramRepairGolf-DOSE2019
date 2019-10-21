
package unrc.dose;

import org.javalite.activejdbc.Model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
* Password class represents the way to keep the safety of the security on the system.
*/

public class Password extends Model {

  /**
  * The value of RANDOM is {@value}.
  */
  private static final Random RANDOM = new SecureRandom();
  /**
  * The value of ITERATIONS is {@value}.
  */
  private static final int ITERATIONS = 10000;
  /**
  * The value of KEY_LENGTH is {@value}.
  */
  private static final int KEY_LENGTH = 256;
  /**
  * The value of BYTE_LENGTH is {@value}.
  */
  private static final int BYTE_LENGTH = 16;
  /**
  * The value of LIMIT_ONE is {@value}.
  */
  private static final int LIMIT_ONE = 62;
  /**
  * The value of LIMIT_TWO is {@value}.
  */
  private static final int LIMIT_TWO = 9;
  /**
  * The value of LIMIT_THREE is {@value}.
  */
  private static final int LIMIT_THREE = 10;
  /**
  * The value of LIMIT_FOUR is {@value}.
  */
  private static final int LIMIT_FOUR = 36;

  /**
   * Returns a random salt to be used to hash a password.
   *
   * @return a 16 bytes random salt
   */
  public static byte[] getNextSalt() {
    byte[] salt = new byte[BYTE_LENGTH];
    RANDOM.nextBytes(salt);
    return salt;
  }

  /**
   * Returns a salted and hashed password using the provided hash.<br>
   * Note - side effect: the password is destroyed
   * (the char[] is filled with zeros)
   *
   * @param password the password to be hashed
   * @param salt     a 16 bytes salt,
   * ideally obtained with the getNextSalt method
   *
   * @return the hashed password with a pinch of salt
   */
  public static byte[] hash(final char[] password, final byte[] salt) {
    PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
    Arrays.fill(password, Character.MIN_VALUE);
    try {
      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      return skf.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new AssertionError(
        "Error while hashing a password: " + e.getMessage(), e);
    } finally {
      spec.clearPassword();
    }
  }

  /**
   * Returns true if the given password and
   * salt match the hashed value, false otherwise.<br>
   * Note - side effect: the password is
   * destroyed (the char[] is filled with zeros)
   *
   * @param password     the password to check
   * @param salt         the salt used to hash the password
   * @param expectedHash the expected hashed value of the password
   *
   * @return true if the given password and
   * salt match the hashed value, false otherwise
   */
  public static boolean isExpectedPassword(final char[] password,
    final byte[] salt, final byte[] expectedHash) {
    byte[] pwdHash = hash(password, salt);
    Arrays.fill(password, Character.MIN_VALUE);
    if (pwdHash.length != expectedHash.length) {
      return false;
    }
    for (int i = 0; i < pwdHash.length; i++) {
      if (pwdHash[i] != expectedHash[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Generates a random password of a given length, using letters and digits.
   *
   * @param length the length of the password
   *
   * @return a random password
   */
  public static String generateRandomPassword(final int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int c = RANDOM.nextInt(LIMIT_ONE);
      if (c <= LIMIT_TWO) {
        sb.append(String.valueOf(c));
      } else if (c < LIMIT_FOUR) {
        sb.append((char) ('a' + c - LIMIT_THREE));
      } else {
        sb.append((char) ('A' + c - LIMIT_FOUR));
      }
    }
    return sb.toString();
  }
}
