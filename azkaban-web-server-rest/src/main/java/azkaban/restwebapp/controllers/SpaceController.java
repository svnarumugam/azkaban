package azkaban.restwebapp.controllers;

import azkaban.restwebapp.models.Space;
import azkaban.restwebapp.services.SpaceService;
import azkaban.user.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SpaceController {

  @Autowired
  private SpaceService spaceService;

  @PostMapping("/spaces")
  @ResponseStatus(code = HttpStatus.CREATED)
  public Space createSpace(@RequestBody Space request) {
    /* Ideally this user object should be fetched from the
       session and given to service. */
    return spaceService.create(request, new User("sarumuga"));
  }

  @GetMapping("/spaces/{spaceId}")
  public Space getSpace(@PathVariable("spaceId") Integer spaceId) {
    return spaceService.getSpace(spaceId);
  }

  @GetMapping("/spaces")
  public List<Space> getAllSpaces() {
    return spaceService.getAllSpaces();
  }
}
