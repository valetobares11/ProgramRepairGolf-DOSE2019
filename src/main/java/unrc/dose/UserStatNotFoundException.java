package unrc.dose;

/**
 * UserStatException throws when There is not statistic for the user.
 * @author Nahuel Alvarez, Borda Agustin, Castillo Conrado
 */
public final class UserStatNotFoundException extends UserStatException {

    /**
     * serialVersionUID for UserStatNotFoundException.
     */
    private static final long serialVersionUID = 404L;

    /**
     * 404 NOT FOUND.
     */
    private static final int CODE = 404;

    /**
     * Constructs a new DuplicateUserStat exception with the
     * specified detail message.
     * @param userId the id of the user.
     */
    public UserStatNotFoundException(final String userId) {
        super("There is not statistic for the user " + userId);
    }

    @Override
    public int getCode() {
        return CODE;
    }

}
