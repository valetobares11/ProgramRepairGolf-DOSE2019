package unrc.dose;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;
import com.google.gson.Gson;

import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;

public class PropositionEndpoint implements Endpoint {

    static final Logger LOGGER = LoggerFactory.getLogger(PropositionEndpoint.class);

    // private static final String NAME_SPACE = "/proposition";

    @Override
    public void bind(final SparkSwagger restApi) {
        
        restApi.endpoint(
            endpointPath("/")
                .withDescription(
                    "proposition REST API exposing all propositions utilities"
                ),
            (q, a) -> LOGGER.info("Logging Received request for Proposition REST API")
        )
        .get(
            path("/users/:id/challenge/:idChallenge/propsitions"),
            (req, res) -> {
                return "hola";
            }
        )
        .post(
            path("/users/:id/challenge/:idChallenge/propsitions")
                .withDescription("Will return one proposition")
                .withResponseType(String.class),
                (req, res) -> {
                    Proposition propositionService;
                    int idUser = Integer.parseInt(req.params(":idUser"));
                    int challengerId = Integer.parseInt(req.params(":idChallenge"));
                    LazyList<Proposition> propNotSolution = Proposition.getUnsubmittedChallengePropByUser(idUser, challengerId);
                    if (propNotSolution.isEmpty()) {
                        propositionService = Proposition.newProposition(idUser, challengerId);
                        return new Gson().toJson(propositionService.getMapProposition());
                    }
                    propositionService = propNotSolution.get(0);
                    return new Gson().toJson(propositionService.getMapProposition());
                }
        )
        .get(
            path("/proposition/:id/compile")
                .withDescription("Will return true if the source compile")
                .withQueryParam()
                   .withName("source").and()
                .withResponseType(String.class),
            (req, res) -> {
                int idProp = Integer.parseInt(req.queryParams("idProp"));
                String currentCode = req.queryParams("source");
                Proposition propositionService = Proposition.searchByIdProposition(idProp);
                // String className = Challenge.findById(propositionService.get("challenge_id"));
                // return new Gson.toJson(propositionService.compileProposition(currentCode, proposedClassName));
                return "HOla";
            }
        );
    }
}