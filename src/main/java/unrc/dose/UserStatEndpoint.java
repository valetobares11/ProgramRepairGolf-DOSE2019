package unrc.dose;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;

import javax.xml.transform.OutputKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;

/**
 * Endpoint for UserStat.
 * @author Nahuel Alvarez, Borda Agustin, Castillo Conrado
 */
public final class UserStatEndpoint implements Endpoint {

    /** The logger. */
    static final Logger LOG = LoggerFactory.getLogger(UserStatEndpoint.class);

    /** main namespace of this endpoint. */
    private static final String NAME_SPACE = "/userstats";

    @Override
    public void bind(final SparkSwagger restApi) {
        restApi.endpoint(
            endpointPath(NAME_SPACE)
            .withDescription(
                "UserStat REST API exposing all UserStat utilities"
                ),
            (q, a) -> LOG.info("Logging Received request for UserStat Rest API")
        )
        .get(
            path("/export")
            .withDescription("Will return all UserStats")
            .withResponseType(String.class),
            (req, res) -> UserStatService.getBests(null)
        )
        .get(
            path("/ranking")
            .withDescription("Will return the best scores")
            .withQueryParam()
            .withName("number")
            .withObject(Integer.class)
            .withDescription("Number of user scores to get").and()
            .withResponseType(String.class),
            (req, res) -> UserStatService.getBests(req.queryParams("number"))
        )
        .get(
            path("/score")
            .withDescription("Will return the score of a user")
            .withQueryParam()
            .withName("id")
            .withObject(Integer.class)
            .withDescription("id of the user").and()
            .withResponseType(String.class),
            (req, res) -> UserStatService.getScore(req.queryParams("id"))
        )
        .get(
            path("")
            .withDescription("Will return the userStat of a user")
            .withQueryParam()
            .withName("id")
            .withObject(Integer.class)
            .withDescription("id of the user").and()
            .withResponseType(String.class),
            (req, res) -> UserStatService.getUserStat(req.queryParams("id"))
        )
        .post(
            path("")
            .withDescription("Creates a new UserStat")
            .withQueryParam()
            .withName("id")
            .withObject(Integer.class)
            .withDescription("id of UserStat's user").and()
            .withResponseType(UserStat.class),
            (req, res) -> UserStatService.createUserStat(req.queryParams("id"))
        );
    }
}
