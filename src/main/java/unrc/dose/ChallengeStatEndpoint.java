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
        ///:challengeid param
        .post(
            path("/new/:challengeId")
                .withDescription("Will create the corresponding challenge stats")
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

                return result;//gson.toJson(ChallengeStat.getChallengeStat(1));
            }
        )

        .post(
            path("/updateAverageScore/:propositionId")
                .withDescription("Will update challenge stats")
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
                .withResponseType(ChallengeStat.class),
            (req, res) -> {
                int challengeId = Integer.parseInt(req.params(":challengeId"));

                ChallengeStat cs = ChallengeStat.getChallengeStat(challengeId);
                //convertir cs a json y guardar en result
                
                return cs;
            }
        );
    }
}
