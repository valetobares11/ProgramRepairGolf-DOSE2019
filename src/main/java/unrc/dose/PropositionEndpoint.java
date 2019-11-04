package unrc.dose;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;
import com.google.gson.Gson;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;

/**
 * In this class we define endpoints for propositions.
 * @author Boaglio, Mercau, Saenz.
 *
 */
public class PropositionEndpoint implements Endpoint {

    /**
     * Constant logger.
     */
    static final Logger LOGGER = LoggerFactory.getLogger(
            PropositionEndpoint.class);

    @Override
    public void bind(final SparkSwagger restApi) {

        restApi.endpoint(
                endpointPath("/")
                .withDescription(
                        "proposition REST API exposing"
                        + " all propositions utilities"
                        ),
                (q, a) -> LOGGER.info(
                        "Logging Received request for Proposition REST API")
                )
        .get(
                path("/user/:idUser/challenge/:idChallenge/proposition")
                .withDescription(
                        "Will return a list with all solutions for "
                        + "one user in the challenge ")
                .withResponseType(String.class),
                (req, res) -> {
                    int userId = Integer.parseInt(req.params(":idUser"));
                    int challengeId =
                            Integer.parseInt(req.params(":idChallenge"));
                    LazyList<Proposition> listSol =
                            Proposition.getChallengeSolutionsByUser(
                                    userId, challengeId);
                    return JsonHelper.toJsonString(
                            Proposition.listJson(listSol));
                }
                )
        .get(
                path("/challenge/:idChallenge/proposition")
                .withDescription(
                        "Will return a list with"
                        + " all solution for one challenge")
                .withResponseType(String.class),
                (req, res) -> {
                    int challengeId = Integer.parseInt(
                            req.params(":idChallenge"));
                    LazyList<Proposition> listSolChallenge =
                            Proposition.getAllSolutionsForChallenge(
                                    challengeId);
                    return JsonHelper.toJsonString(
                            Proposition.listJson(listSolChallenge));
                }
                )
        .get(
                path("/user/:idUser/proposition")
                .withDescription(
                        "Will return a list with all solution of one user")
                .withResponseType(String.class),
                (req, res) -> {
                    int userId = Integer.parseInt(req.params(":idUser"));
                    LazyList<Proposition> listSolUser =
                            Proposition.getSolutionsFromAUser(userId);
                    return JsonHelper.toJsonString(
                            Proposition.listJson(listSolUser));
                }
                )
        .post(
                path("/users/:idUser/challenge/:idChallenge/propsitions")
                .withDescription("Will return one proposition")
                .withResponseType(String.class),
                (req, res) -> {
                    Proposition propositionService;
                    int idUser = Integer.parseInt(req.params(":idUser"));
                    int challengerId = Integer.parseInt(
                            req.params(":idChallenge"));
                    LazyList<Proposition> propNotSolution =
                            Proposition.getUnsubmittedChallengePropByUser(
                                    idUser, challengerId);
                    if (propNotSolution.isEmpty()) {
                        propositionService = Proposition.newProposition(
                                idUser, challengerId);
                        return new Gson().toJson(
                                propositionService.getMapProposition());
                    }
                    propositionService = propNotSolution.get(0);
                    return new Gson().toJson(
                            propositionService.getMapProposition());
                }
                )
        .get(
                path("/proposition/:id/compile")
                .withDescription("Will return true if the source compile")
                .withQueryParam()
                .withName("source").and()
                .withResponseType(String.class),
                (req, res) -> {
                    int idProp = Integer.parseInt(req.params(":id"));
                    String currentCode = req.queryParams("source");
                    Proposition propositionService =
                            Proposition.searchByIdProposition(idProp);
                    Challenge chall = Challenge.findById(
                            propositionService.getChallengeId());
                    String className = chall.getClassName();
                    return new Gson().toJson(
                            propositionService.compileProposition(
                                    currentCode, className));
                }
                )
        .put(
                path("/proposition/:id/submit")
                .withDescription(
                        "Will return true if the"
                        + " source is solution for the challenge")
                .withQueryParam()
                .withName("source").and()
                .withResponseType(String.class),
                (req, res) -> {
                    int idProp = Integer.parseInt(req.params(":id"));
                    String currentCode = req.queryParams("source");
                    Proposition propositionService =
                            Proposition.searchByIdProposition(idProp);
                    Challenge chall = Challenge.findById(
                            propositionService.getChallengeId());
                    String className = chall.getClassName();
                    return new Gson().toJson(
                            propositionService.submitProposition(
                                    currentCode, className));
                }
                );
    }
}
