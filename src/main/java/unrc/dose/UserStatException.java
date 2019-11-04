package unrc.dose;

/**
 * Abstract for UserStat errors.
 * @author Nahuel Alvarez, Borda Agustin, Castillo Conrado
 */
public abstract class UserStatException extends RuntimeException {

    /**
     * serialVersionUID for UserStatException.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Return the http code of the exception.
     * @return the http code.
     */
    public abstract int getCode();

    /**
     * Constructs a new UserStat exception with the specified detail message.
     * @param message the detail message.
     */
    public UserStatException(final String message) {
        super(message);
    }
}
