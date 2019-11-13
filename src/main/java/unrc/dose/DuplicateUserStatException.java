package unrc.dose;

/**
 * UserStatException throws when there are statistics already.
 * defined for the user.
 * @author Nahuel Alvarez, Borda Agustin, Castillo Conrado
 */
public final class DuplicateUserStatException extends UserStatException {

    /**
     * serialVersionUID for DuplicateUserStatException.
     */
    private static final long serialVersionUID = 409L;

    /**
     * 409 CONFLICT.
     */
    private static final int CODE = 409;

    /**
     * Constructs a new DuplicateUserStat exception with the
     * specified detail message.
     * @param userId the id of the user.
     */
    public DuplicateUserStatException(final String userId) {
        super("There are statistics already defined for the user " + userId);
    }

    @Override
    public int getCode() {
        return CODE;
    }

}
