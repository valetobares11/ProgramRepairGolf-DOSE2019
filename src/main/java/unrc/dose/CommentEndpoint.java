package unrc.dose;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;
import static com.beerboy.ss.descriptor.ParameterDescriptor.Builder.newBuilder;
import org.javalite.activejdbc.LazyList;

/** Endpoint wrapping everything related to Comments. */
public final class CommentEndpoint implements Endpoint {

  /** logger... */
  static final Logger LOGGER = LoggerFactory.getLogger(CommentEndpoint.class);

  /** main namespace of this endpoint. */
  private static final String NAME_SPACE = "/comments";

  /** service used to manipulate in memory the bellies. */
  private static Comment commentService = new Comment();

  @Override
  public void bind(final SparkSwagger restApi) {

      restApi.endpoint(
          endpointPath(NAME_SPACE)
              .withDescription(
                  "comment REST API exposing all comments utilities"
              ),
          (q, a) -> LOGGER.info("Logging Received request for Comment Rest API")
      )
      ////////////////////////////////////
      .get(
          path("/users/:id")
              .withDescription("Will return all comments of user's id")
              .withPathParam()
                  .withName("id")
                  .withDescription("user's id").and()
              .withResponseType(Gson.class),
          (req, res) -> {
              return new Gson().toJson(commentService.viewComment(Integer.parseInt(req.params(":id")), new User()));
          }
      )
      .get(
         path("/challenges/:id")
             .withDescription("Will return all comments of user's id")
             .withPathParam()
                 .withName("id")
                 .withDescription("challenge's id").and()
             .withResponseType(Gson.class),
         (req, res) -> {
             return new Gson().toJson(commentService.viewComment(Integer.parseInt(req.params(":id")), new Challenge()));
         }
      )
      .get(
         path("/reponses/:id")
             .withDescription("Will return all responses of comment's id")
             .withPathParam()
                 .withName("id")
                 .withDescription("comment's id").and()
             .withResponseType(Gson.class),
         (req, res) -> {
             return new Gson().toJson(commentService.viewComment(Integer.parseInt(req.params(":id")), new Comment()));
         }
      )
      .post(
          path("/createComment")
              .withDescription("Creates a new Comment")
              .withQueryParam()
                  .withName("title")
                  .withDescription("Comment's title")
                  .withName("description")
                  .withDescription("Comment's body")
                  .withName("challengeId")
                  .withDescription("challenge's id which is commented")
                  .withName("userId")
                  .withDescription(" user's id who commented").and()
              .withResponseType(Gson.class),
          (req, res) -> {
              return new Gson().toJson(
                  commentService.createComment(req.queryParams("title"),
                    req.queryParams("description"),Integer.parseInt(req.queryParams("challengeId")),Integer.parseInt(req.queryParams("userId")))
              );
          }
      )
      .post(
          path("/createResponse")
              .withDescription("Creates a new Response")
              .withQueryParam()
                  .withName("description")
                  .withDescription("Response's body")
                  .withName("userId")
                  .withDescription(" user's id who commented")
                  .withName("commentId")
                  .withDescription("comment's id witch is responsed").and()
              .withResponseType(Gson.class),
          (req, res) -> {
              return new Gson().toJson(
                  commentService.createResponse(req.queryParams(
                    "description"),Integer.parseInt(req.queryParams("userId")),Integer.parseInt(req.queryParams("commentId")))
              );
          }
      );
  }

}
