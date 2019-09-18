package unrc.dose;

import org.javalite.activejdbc.Model;

public class Proposition extends Model {

	private final int userId;
	private final int challengerId;
	private String source;

	/**
	*	CONSTRUCTOR
	**/
	public Proposition(int idUser, int idChallenge) {
		userId = idUser;
		challengerId = idChallenge;
		source = null;
	}

}