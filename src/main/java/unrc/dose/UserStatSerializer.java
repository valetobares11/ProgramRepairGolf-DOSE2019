package unrc.dose;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Create a json representation  of User Stat.
 * @author Nahuel Alvarez, Borda Agustin, Castillo Conrado
 */
public final class UserStatSerializer implements JsonSerializer<UserStat> {
    @Override
    public JsonElement serialize(final UserStat src, final Type typeOfSrc,
            final JsonSerializationContext context) {
        JsonObject jsonUserStat = new JsonObject();
        jsonUserStat.addProperty(UserStat.USERID,
                src.getUserId());
        jsonUserStat.addProperty(UserStat.CREATEDCHALLENGES,
                src.getCreatedChallenges());
        jsonUserStat.addProperty(UserStat.SOLVEDCHALLENGES,
                src.getSolvedChallenges());
        jsonUserStat.addProperty(UserStat.CURRENTPOINTS,
                src.getCurrentPoints());
        return jsonUserStat;
    }
}
