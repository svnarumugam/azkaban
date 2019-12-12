package azkaban.restwebapp.controllers;

import azkaban.project.Project;
import azkaban.restwebapp.models.Space;
import azkaban.restwebapp.services.SpaceService;
import azkaban.user.User;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpaceController {

  @Autowired
  private SpaceService spaceService;

  @PostMapping("/spaces")
  @ResponseStatus(code = HttpStatus.CREATED)
  public Space createSpace(@RequestBody Space request) {
    return new Space(1, request.getName(), request.getDescription());
  }

  @GetMapping("/spaces/{spaceId}")
  public Space getSpace(@PathVariable("spaceId") Integer spaceId) {
    return new Space(spaceId, "test", "test-description");
  }

  @GetMapping("/spaces")
  public List<Space> getAllSpaces() {
    return ImmutableList.of(
        new Space(1, "test", "test-description"),
        new Space(2, "test-2", "test-description-2")
    );
  }

  // test to see if we are able to inject service and access database
  // semantically wrong as it creates a record in the db
  // only for testing
  @GetMapping("/spaces/dbtest")
  public Project dbAccessTest() {
    return spaceService.createNewProject(
        "test-project-hello",
        "description",
        new User("azkaban")
    );
  }
}
