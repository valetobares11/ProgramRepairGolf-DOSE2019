package unrc.dose;

import java.io.Externalizable;

import org.javalite.activejdbc.LazyList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;

/**
 * Intermediate class between UserStat Model and the Endpoint.
 * @author Nahuel Alvarez, Borda Agustin, Castillo Conrado
 */
public final class UserStatService {

    /** The constructor. **/
    private UserStatService() { }

    /**
     * Create the user statistics for a user and return it.
     * @param userid the user who want to create the statistics
     * @return the statistic in json format
     */
    public static String createUserStat(final String userid) {
        UserStat userStat = UserStat.createUserStat(Integer.valueOf(userid));
        return userStatToJson(userStat);
    }

    /**
     * Get the userStat of an user.
     * @param userid id of the user
     * @return the statistics in json format
     */
    public static String getUserStat(final String userid) {
        UserStat userStat = UserStat.getUserStat(Integer.valueOf(userid));
        return userStatToJson(userStat);
    }

    /**
     * Get the score (current points) of a user.
     * @param userid user who want to get the score
     * @return current points in json format
     */
    public static String getScore(final String userid) {
        JsonParser parser = new JsonParser();
        JsonObject userStat = parser.parse(getUserStat(userid)).
                getAsJsonObject();
        return subJsonObject(userStat, "current_points").toString();
    }

    /**
     * Return the x users with the best scores.
     * @param number The number of users.
     * @return the list of best usersStats in json format
     */
    public static String getBests(final String number) {
        Integer x = number == null ? null : Integer.valueOf(number);
        LazyList<UserStat> userStats = UserStat.showBestScores(x);
        return userStatToJson(userStats);
    }

    /**
     * Transform a userStat or userStat List in a json.
     * @param userStat object to transform
     * @return json format of userStat
     */
    public static String userStatToJson(final Externalizable userStat) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonSerializer<UserStat> serializer = new UserStatSerializer();
        gsonBuilder.registerTypeAdapter(UserStat.class, serializer);
        Gson customGson = gsonBuilder.create();
        return customGson.toJson(userStat);
    }

    /**
     * Returns the member with the specified name in a json.
     * @param jsonObject json from which get a subjson
     * @param memberName name of the member
     * @return json containing the member
     */
    public static JsonObject subJsonObject(final JsonObject jsonObject,
            final String memberName) {
        JsonObject object = new JsonObject();
        object.add(memberName, jsonObject.get(memberName));
        return object;
    }

    public static void delete(final String userid) {
 
    }

}
