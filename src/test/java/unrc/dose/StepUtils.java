package unrc.dose;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Base64;

import spark.utils.IOUtils;

class StepUtils {
  private static final String PORT = "4567";
  private static final String HOST = "localhost";
  private static final String PROTOCOL = "http";

  /**
   * Make a http request to a server
   *
   * @param  requestMethod  HTTP VERB to use (GET, POST, PUT, PATCH...)
   * @param  path the endpoint
   * @param  body [Optional] the payload if needed
   * @return      UrlResponse
   */
  public static UrlResponse doRequest(String requestMethod, String path, Map body) {
    UrlResponse response = new UrlResponse();

    try {
        getResponse(requestMethod, path, body, response);
    } catch (IOException e) {
        e.printStackTrace();
    }

    return response;
  }

  public static void getResponse(String requestMethod, String path, Map body, UrlResponse response)
          throws MalformedURLException, IOException, ProtocolException {
    URL url = new URL(PROTOCOL + "://" + HOST + ":" + PORT + path);

    // This is the point where the connection is opened.
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    // set connection verb to user
    connection.setRequestMethod(requestMethod);

    // When a get is executed there is no body to attach
    if (requestMethod != "GET") {
      // set connection output to true (needs to be true since this request
      // is carrying an input (response) body.)
      connection.setDoOutput(true);

      // write parameters into connection
      OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
      writer.write(body.toString());
      writer.close();
    }

    // Open communications link (network traffic occurs here).
    connection.connect();

    //  Retrieve the response body as an InputStream.
    String res = IOUtils.toString(connection.getInputStream());

    // Build the response to return
    response.body = res;
    response.status = connection.getResponseCode();
    response.headers = connection.getHeaderFields();
  }

  // Represent a Response
  public static class UrlResponse {
    public Map<String, List<String>> headers;
    public String body;
    public int status;
  }
}