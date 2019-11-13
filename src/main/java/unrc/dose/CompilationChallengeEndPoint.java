package unrc.dose;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;

import java.util.List;
import java.util.Map;

import org.javalite.common.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;

/**
 * Endpoint for CompilationChallenge.
 * @author Brusati Formento, Matias
 * @author Cuesta, Alvaro
 */
public final class CompilationChallengeEndPoint implements Endpoint {

    /** logger... */
    static final Logger LOGGER = LoggerFactory.getLogger(
        CompilationChallengeEndPoint.class);

    /** main namespace of this endpoint. */
    private static final String NAME_SPACE = "/compilationChallenge";

    @Override
    public void bind(final SparkSwagger restApi) {

        restApi.endpoint(
            endpointPath(NAME_SPACE)
                .withDescription(
                    "compilationChallege REST API exposing all "
                    + "CompilationChallenge utilities"
                ),
            (q, a) -> LOGGER.info(
                "Logging Received request for CompilationChallenge Rest API")
        )
        .post(
            path("/create")
                .withDescription("Creates a new compilation challenge")
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
                .withResponseType(String.class),
            (req, res) -> {
                return CompilationChallenge.addCompilationChallenge(
                    Integer.parseInt(req.queryParams("userId")),
                    req.queryParams("title"),
                    req.queryParams("className"),
                    req.queryParams("description"),
                    req.queryParams("source"),
                    Integer.parseInt(req.queryParams("point")),
                    Integer.parseInt(req.queryParams("ownerSolutionId")));
            }
        )
        .put(
            path("/modify")
                .withDescription("Modify a unsolved compilation Challenge")
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
                .withResponseType(String.class),
            (req, res) -> {
                return CompilationChallenge.modifyUnsolvedCompilationChallenge(
                    Integer.parseInt(req.queryParams("id")),
                    req.queryParams("title"),
                    req.queryParams("className"),
                    req.queryParams("description"),
                    req.queryParams("source"),
                    Integer.parseInt(req.queryParams("point")));
            }
        )
        .get(
            path("/all")
                .withDescription("Will return all compilation challenge")
                .withResponseType(String.class),
            (req, res) -> {
                List<Map<String, Object>> all =
                CompilationChallenge.viewAllCompilationChallange();
                return JsonHelper.toJsonString(all);
            }
        )
        .get(
            path("/resolved")
                .withDescription("Will return all compilation challenge solved")
                .withResponseType(String.class),
            (req, res) -> {
                List<Map<String, Object>> resolved =
                CompilationChallenge.viewResolvedCompilationChallange();
                return JsonHelper.toJsonString(resolved);
            }
        )
        .get(
            path("/unsolved")
                .withDescription("Will return all compilation challenge solved")
                .withResponseType(String.class),
            (req, res) -> {
                List<Map<String, Object>> unsolved =
                CompilationChallenge.viewUnsolvedCompilationChallange();
                return JsonHelper.toJsonString(unsolved);
            }
        );
    }
}
