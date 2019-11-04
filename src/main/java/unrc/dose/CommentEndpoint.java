package unrc.dose;

import static com.beerboy.ss.descriptor.EndpointDescriptor.endpointPath;
import static com.beerboy.ss.descriptor.MethodDescriptor.path;

import com.beerboy.ss.SparkSwagger;
import com.beerboy.ss.rest.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *Endpoint wrapping everything related to Comments.
 */
public final class CommentEndpoint implements Endpoint {

  /**logger... */
  static final Logger LOGGER = LoggerFactory.getLogger(CommentEndpoint.class);

  /**main namespace of this endpoint. */
  private static final String NAME_SPACE = "/comments";

  /**service used to manipulate in memory the bellies.
   */
  private static CommentService commentService = new CommentService();

  @Override
  public void bind(final SparkSwagger restApi) {

    restApi.endpoint(
          endpointPath(NAME_SPACE)
              .withDescription(
                  "comment REST API exposing all comments utilities"
              ),
        (q, a) -> LOGGER.info("Logging Received request for Comment Rest API")
      )
        .get(
          path("/users/:id")
              .withDescription("Will return all comments of user's id")
              .withPathParam()
                  .withName("id")
                  .withDescription("user's id").and()
              .withResponseType(String.class),
          (req, res) -> {
              return commentService.view(
                Integer.parseInt(req.params(":id")), new User());
          }
      )
        .get(
         path("/challenges/:id")
             .withDescription("Will return all comments of user's id")
             .withPathParam()
                 .withName("id")
                 .withDescription("challenge's id").and()
             .withResponseType(String.class),
          (req, res) -> {
            return commentService.view(
              Integer.parseInt(req.params(":id")), new Challenge());
          }
      )
        .get(
         path("/responses/:id")
             .withDescription("Will return all responses of comment's id")
             .withPathParam()
                 .withName("id")
                 .withDescription("comment's id").and()
             .withResponseType(String.class),
          (req, res) -> {
            return commentService.view(
              Integer.parseInt(req.params(":id")), new Comment());
          }
      )
        .post(
          path("/createComment")
              .withDescription("Creates a new Comment")
              .withQueryParam()
                  .withName("title")
                  .withDescription("Comment's title").and()
                .withQueryParam()
                  .withName("description")
                  .withDescription("Comment's body").and()
                .withQueryParam()
                  .withName("challengeId")
                  .withDescription("challenge's id which is commented").and()
                .withQueryParam()
                  .withName("userId")
                  .withDescription(" user's id who commented").and()
              .withResponseType(String.class),
          (req, res) -> {
              return commentService.comment(req.queryParams("title"),
                     req.queryParams("description"),
                     Integer.parseInt(req.queryParams("challengeId")),
                     Integer.parseInt(req.queryParams("userId")));

          }
      )
        .post(
          path("/createResponse")
              .withDescription("Creates a new Response")
              .withQueryParam()
                  .withName("description")
                  .withDescription("Response's body").and()
              .withQueryParam()
                  .withName("userId")
                  .withDescription(" user's id who commented").and()
              .withQueryParam()
                  .withName("commentId")
                  .withDescription("comment's id witch is responsed").and()
              .withResponseType(String.class),
               "application/json",
          (req, res) -> {
              return commentService.response(req.queryParams(
                    "description"), Integer.parseInt(req.queryParams("userId")),
                      Integer.parseInt(req.queryParams("commentId")));
          }
      );
  }

}
