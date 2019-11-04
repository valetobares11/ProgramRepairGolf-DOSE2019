package unrc.dose;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;
import com.google.gson.Gson;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;

/** Endpoint wrapping everything related to Bellies. */
public final class ChallengeStatEndpoint implements Endpoint {
    /** logger... */
    static final Logger LOGGER = LoggerFactory.getLogger(ChallengeStatEndpoint.class);

    /** main namespace of this endpoint. */
    private static final String NAME_SPACE = "/challengestat";


    @Override
    public void bind(final SparkSwagger restApi) {

        restApi.endpoint(
            endpointPath(NAME_SPACE)
                .withDescription(""),
            (q, a) -> LOGGER.info("Logging Received request for Belly Rest API")
        )

        .post(
            path("/new/:challengeId")
                .withDescription("It will create the corresponding challenge stats")
                .withPathParam()
                    .withName("challengeId")
                    .withDescription("The challenge id to create its corresponding stats table")
                    .withObject(String.class).and()
                .withResponseType(String.class),
            (req, res) -> {
                String result = "";
                int challengeId = Integer.parseInt(req.params(":challengeId"));
                ChallengeStat.newChallengeStat(challengeId);
                if (ChallengeStat.getChallengeStat(challengeId) != null) {
                    result = "Challenge stats sucessfully created!";
                } else {
                    result = "Something went wrong";
                }

                return result;
            }
        )

        .put(
            path("/updateAverageScore/:propositionId")
                .withDescription("It will update the average score of a challenge stats")
                .withPathParam()
                    .withName("propositionId")
                    .withDescription("The proposition id to update the corresponding challenge stats")
                    .withObject(String.class).and()
                .withResponseType(String.class),
            (req, res) -> {
                String result = "";
                int propositionId = Integer.parseInt(req.params(":propositionId"));

                ChallengeStat.updateAverageScore(propositionId);

                result = "Average score sucessfully updated!";

                return result;
            }
        )

        .post(
            path("/get/:challengeId")
                .withDescription("Will fetch challenge stat data")
                .withPathParam()
                    .withName("challengeId")
                    .withDescription("The challenge id to get its corresponding stats")
                    .withObject(String.class).and()
                .withResponseType(String.class),
            (req, res) -> {
                int challengeId = Integer.parseInt(req.params(":challengeId"));

                ChallengeStat cs = ChallengeStat.getChallengeStat(challengeId);

                return cs.toJson(true, "id", "challenge_id", "solved_count", "average_score");
            }
        );
    }
}
