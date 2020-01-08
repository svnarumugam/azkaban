package cloudflow.services;

import azkaban.user.User;
import cloudflow.daos.SpaceDao;
import cloudflow.models.Space;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SpaceService {

  private final SpaceDao spaceDao;

  @Inject
  public SpaceService(SpaceDao spaceDao) {
    this.spaceDao = spaceDao;
  }

  public Space create(Space space, User user) {
    int spaceId = spaceDao.createSpace(space, user);
    return getSpace(spaceId);
  }

  public Space getSpace(int spaceId) {
    // perform validation and handle appropriate
    // exceptions here
    // test code
    Optional<Space> space = spaceDao.getSpace(spaceId);
    return space.get();
  }

  public List<Space> getAllSpaces() {
    return spaceDao.getAllSpaces();
  }
}
