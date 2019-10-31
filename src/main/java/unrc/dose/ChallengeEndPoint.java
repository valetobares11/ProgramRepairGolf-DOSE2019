package unrc.dose;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;

import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;

/**
 * Endpoint for CompilationChallenge.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public final class ChallengeEndPoint implements Endpoint {

    /** logger... */
    static final Logger LOGGER =
    LoggerFactory.getLogger(ChallengeEndPoint.class);

    /** main namespace of this endpoint. */
    private static final String NAME_SPACE = "/challenge";

    @Override
    public void bind(final SparkSwagger restApi) {

        restApi.endpoint(
            endpointPath(NAME_SPACE)
                .withDescription(
                    "challege REST API exposing all Challenge utilities"
                ),
            (q, a) -> LOGGER.info(
                "Logging Received request for Challenge Rest API")
        )
        .delete(
            path("/:id")
                .withDescription("Delete a challenge")
                .withPathParam()
                    .withName("id")
                    .withDescription("Challenge's id").and()
                    .withResponseType(String.class),
            (req, res) -> {
                Challenge.deleteChallenge(Integer.parseInt(req.params("id")));
                return "ok";
            }
        )
        .get(
            path("/:userId")
                .withDescription("Show the challenges associated with a user")
                .withPathParam()
                    .withName("userId")
                    .withDescription("user id owner of the challenges").and()
                    .withResponseType(String.class),
            (req, res) -> {
                LazyList<Challenge> list =
                Challenge.viewUserAssociatedChallange(
                Integer.parseInt(req.params("userId")));
                return list;
            }
        );
    }
}
