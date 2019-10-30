package unrc.dose;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;
import com.google.gson.Gson;

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
            path("/stats")
                .withDescription("Will send the corresponding challenge stats")
                .withQueryParam()
                    .withName("challengeid")
                    .withDescription("The challenge id").and()
                .withResponseType(String.class),
            (req, res) -> {

                ChallengeStat cs = ChallengeStat.getChallengeStat(1);
                return "hola mundo";
            }
        );
    }
}
