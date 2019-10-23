/** db-attributes of userStats:
 * id
 * user_id
 * created_challenges
 * solved_challenges
 * current_points
 * created_at
 * updated_at
 */

package unrc.dose;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;


/**
 * Class for UserStat.
 * @author Nahuel Alvarez, Borda Agustin, Castillo Conrado
 */
public class UserStat extends Model {
/* Validators for model */
static {
    validatePresenceOf("user_id");
    validatePresenceOf("created_challenges");
    validatePresenceOf("solved_challenges");
    validatePresenceOf("current_points");
}
    /**
     * Gets the id of the user who has assigned this statistics.
     * in the database.
     * @return The id of the user.
     */
    public int getUserId() {
        return this.getInteger("user_id");
    }

    /**
     * Gets the number of created challenges by the user
     * who has assigned this statistics in the database.
     * @return The number of created challenges.
     */
    public int getCreatedChallenges() {
        return this.getInteger("created_challenges");
    }

    /**
     * Gets the number of solved challenges by the user
     * who has assigned this statistics in the database.
     * @return The number of solved challenges.
     */
    public int getSolvedChallenges() {
        return this.getInteger("solved_challenges");
    }

    /**
     * Gets the points that the user earned solving challenges.
     * @return The points of the user.
     */
    public int getCurrentPoints() {
        return this.getInteger("current_points");
    }

    /**
     * Sets the user_id of the user who wants to set this statistics.
     * @param id The id of the user who statistics we will set.
     */
    public void setUserId(final int id) {
        this.set("user_id", id).saveIt();
    }

    /**
     * Sets the chreated_challenges of the statistics.
     * @param challengesCreated The number of challenges created.
     */
    public void setCreatedChallenges(final int challengesCreated) {
        this.set("created_challenges", challengesCreated).saveIt();
    }

    /**
     * Sets the solved_challenges of the statistics.
     * @param solvedChallenges The number of solved challenges.
     */
    public void setSolvedChallenges(final int solvedChallenges) {
        this.set("solved_challenges", solvedChallenges).saveIt();
    }

    /**
     * Sets the current_points of the statistics.
     * @param currentPoints The number of curret points.
     */
    public void setCurrentPoints(final int currentPoints) {
        this.set("current_points", currentPoints).saveIt();
    }

    /**
     * Show all UserStat registered in the system.
     * @return LazyList with all the UserStat.
     */
    public static LazyList<UserStat> showAllUserStat() {
        LazyList<UserStat> users = UserStat.findAll();
        return users;
    }

    /**
     * Create the statistics for a given user.
     * @param id The id of the user who statistics we will create.
     * @return the created user statistics.
     */
    public static UserStat createUserStat(final int id) {
        UserStat stat = new UserStat();
        stat.set("user_id", id);
        stat.set("created_challenges", 0);
        stat.set("solved_challenges", 0);
        stat.set("current_points", 0);
        stat.saveIt();
        return stat;
    }

    /**
     * This method return the statics of a user.
     * @param user The user from who wants to get the statistics
     * @return Statistics of the user
     */
    public static UserStat getUserStat(final User user) {
        return UserStat.findFirst("user_id = ?", user.getId());
    }

    /**
     * This method return the x users with the best scores.
     * @param x The number of users.
     * @return A LazyList of the userStats with the higest scores.
     */
    public static LazyList<UserStat> showBestScores(final int x) {
      LazyList<UserStat> userStats = UserStat.findAll().orderBy(
      "current_points desc").limit(x);

      return userStats;
    }

}
