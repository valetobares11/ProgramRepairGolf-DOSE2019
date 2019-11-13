package unrc.dose;

/**
 * UserNotFoundException throws when There are not users with the id.
 * @author Nahuel Alvarez, Borda Agustin, Castillo Conrado
 */
public final class UserNotFoundException extends UserStatException {

    /**
     * serialVersionUID for UserStatException.
     */
    private static final long serialVersionUID = 404L;

    /**
     * 404 NOT FOUND.
     */
    private static final int CODE = 404;

    /**
     * Constructs a new UserNotFound exception with the
     * specified detail message.
     * @param userId the id of the user.
     */
    public UserNotFoundException(final String userId) {
        super("There are not users with the id " + userId);
    }

    @Override
    public int getCode() {
        return CODE;
    }

}
