package unrc.dose;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;

/**
 * Endpoint for TestChallenge.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public final class TestChallengeEndPoint implements Endpoint {

    /** logger... */
    static final Logger LOGGER = LoggerFactory.getLogger(
        TestChallengeEndPoint.class);

    /** main namespace of this endpoint. */
    private static final String NAME_SPACE = "/testChallenge";

    @Override
    public void bind(final SparkSwagger restApi) {

        restApi.endpoint(
            endpointPath(NAME_SPACE)
                .withDescription(
                    "testChallege REST API exposing all TestChallenge utilities"
                ),
            (q, a) -> LOGGER.info(
                "Logging Received request for TestChallege Rest API")
        )
        .post(
            path("/create")
                .withDescription("Creates a new test challenge")
                .withQueryParam()
                    .withName("userId")
                    .withDescription("Challenge's owner").and()
                .withQueryParam()
                    .withName("title")
                    .withDescription("challenge title").and()
                .withQueryParam()
                    .withName("className")
                    .withDescription(
                    "class name with which the file will be saved").and()
                .withQueryParam()
                    .withName("description")
                    .withDescription("challenge description").and()
                .withQueryParam()
                    .withName("source")
                    .withDescription("challenge source code").and()
                .withQueryParam()
                    .withName("point")
                    .withDescription("challenge point").and()
                .withQueryParam()
                    .withName("ownerSolutionId")
                    .withDescription("solution owner").and()
                .withQueryParam()
                    .withName("test")
                    .withDescription("challenge test").and()
                .withResponseType(String.class),
            (req, res) -> {
                return TestChallenge.addTestChallenge(
                    Integer.parseInt(req.queryParams("userId")),
                    req.queryParams("title"),
                    req.queryParams("className"),
                    req.queryParams("description"),
                    req.queryParams("source"),
                    Integer.parseInt(req.queryParams("point")),
                    Integer.parseInt(req.queryParams("ownerSolutionId")),
                    req.queryParams("test"));
            }
        )
        .put(
            path("/modify")
                .withDescription("Modify a unsolved test challenge")
                .withQueryParam()
                    .withName("id")
                    .withDescription("Challenge's id").and()
                .withQueryParam()
                    .withName("title")
                    .withDescription("challenge title").and()
                .withQueryParam()
                    .withName("className")
                    .withDescription(
                    "class name with which the file will be saved").and()
                .withQueryParam()
                    .withName("description")
                    .withDescription("challenge description").and()
                .withQueryParam()
                    .withName("source")
                    .withDescription("challenge source code").and()
                .withQueryParam()
                    .withName("point")
                    .withDescription("challenge point").and()
                .withQueryParam()
                    .withName("test")
                    .withDescription("challenge test").and()
                .withResponseType(String.class),
            (req, res) -> {
                return TestChallenge.modifyUnsolvedTestChallenge(
                    Integer.parseInt(req.queryParams("id")),
                    req.queryParams("title"),
                    req.queryParams("className"),
                    req.queryParams("description"),
                    req.queryParams("source"),
                    Integer.parseInt(req.queryParams("point")),
                    req.queryParams("test"));
            }
        )
        .get(
            path("/challenge/:id")
                .withDescription("Return a test challenge")
                .withPathParam()
                    .withName("id")
                    .withDescription("id challenge").and()
                .withResponseType(String.class),
            (req, res) -> {
                TestChallenge t =
                TestChallenge.findFirst("challenge_id = ?", req.params("id"));
                TestChallenge.validatePresenceTestChallenge(t);
                return t.toJson(true, "id", "challenge_id", "test");
            }
        )
        .get(
            path("/all")
                .withDescription("Will return all test challenge")
                .withResponseType(String.class),
            (req, res) -> {
                List<Map<String, String>> all =
                TestChallenge.viewAllTestChallange();
                return all;
            }
        )
        .get(
            path("/resolved")
                .withDescription("Will return all test challenge solved")
                .withResponseType(String.class),
            (req, res) -> {
                List<Map<String, String>> resolved =
                TestChallenge.viewResolvedTestChallange();
                return resolved;
            }
        )
        .get(
            path("/unsolved")
                .withDescription("Will return all test challenge solved")
                .withResponseType(String.class),
            (req, res) -> {
                List<Map<String, String>> unsolved =
                TestChallenge.viewUnsolvedTestChallange();
                return unsolved;
            }
        );
    }
}
