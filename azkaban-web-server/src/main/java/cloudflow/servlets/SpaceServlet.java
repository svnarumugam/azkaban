package cloudflow.servlets;

import azkaban.server.HttpRequestUtils;
import azkaban.server.session.Session;
import azkaban.user.User;
import azkaban.webapp.AzkabanWebServer;
import azkaban.webapp.servlet.LoginAbstractAzkabanServlet;
import cloudflow.SampleService;
import cloudflow.models.Space;
import cloudflow.services.SpaceService;
import com.linkedin.jersey.api.uri.UriTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpaceServlet extends LoginAbstractAzkabanServlet {

  private static final String GET_ALL_SPACE_URI = "/api/v1/spaces";
  private static final String GET_SPACE_URI_TEMPLATE = "/api/v1/spaces/{spaceId}";
  private static final String SPACE_ID_KEY = "spaceId";
  private SampleService sample;
  private SpaceService spaceService;

  private static final Logger log = LoggerFactory.getLogger(SpaceServlet.class);

  @Override
  public void init(final ServletConfig config) throws ServletException {
    super.init(config);
    final AzkabanWebServer server = (AzkabanWebServer) getApplication();
    this.sample = server.sampleService();
    this.spaceService = server.spaceService();
  }

  @Override
  protected void handleGet(HttpServletRequest req, HttpServletResponse resp, Session session)
      throws ServletException, IOException {
    /* there can be two requests here */
    /* Get all records */
    if (GET_ALL_SPACE_URI.equals(req.getRequestURI())) {
      resp.getWriter().println(spaceService.getAllSpaces());
    } else {
      UriTemplate template = new UriTemplate(GET_SPACE_URI_TEMPLATE);
      Map<String, String> map = new HashMap<>();
      template.match(req.getRequestURI(), map);
      int spaceId = Integer.parseInt(map.get(SPACE_ID_KEY));
      resp.getWriter().println(spaceService.getSpace(spaceId));
    }
  }

  @Override
  protected void handlePost(HttpServletRequest req, HttpServletResponse resp, Session session)
      throws ServletException, IOException {
    // we need to perform validations
    // and set response code accordingly
    String body = HttpRequestUtils.getBody(req);
    ObjectMapper objectMapper = new ObjectMapper();
    Space space = objectMapper.readValue(body, Space.class);
    /* ideally the user name should be obtained from session object
    *  session.getUser()
    *  writing user name directly makes testing easier
    * */

    Space createdSpace = spaceService.create(space, new User("sarumuga"));
    resp.setStatus(201);
  }
}
