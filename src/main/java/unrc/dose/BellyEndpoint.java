package unrc.dose;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;

/** Endpoint wrapping everything related to Bellies. */
public final class BellyEndpoint implements Endpoint {
    /** logger... */
    static final Logger LOGGER = LoggerFactory.getLogger(BellyEndpoint.class);

    /** main namespace of this endpoint. */
    private static final String NAME_SPACE = "/belly";

    /** service used to manipulate in memory the bellies. */
    private static BellyService bellyService = new BellyService();

    @Override
    public void bind(final SparkSwagger restApi) {

        restApi.endpoint(
            endpointPath(NAME_SPACE)
                .withDescription(
                    "belly REST API exposing all bellies utilities"
                ),
            (q, a) -> LOGGER.info("Logging Received request for Belly Rest API")
        )
        .get(
            path("/export")
                .withDescription("Will return all bellies in the store")
                .withResponseType(String.class),
            (req, res) -> {
                return new Gson().toJson(bellyService.findAll());
            }
        )
        .post(
            path("")
                .withDescription("Creates a new Belly")
                .withQueryParam()
                    .withName("name")
                    .withDescription("Belly's owner").and()
                .withResponseType(Belly.class),
            (req, res) -> {
                return new Gson().toJson(
                    bellyService.add(req.queryParams("name"))
                );
            }
        );
    }
}
