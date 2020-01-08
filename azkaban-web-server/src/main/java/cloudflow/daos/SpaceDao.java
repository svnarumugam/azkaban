package cloudflow.daos;

import azkaban.db.DatabaseOperator;
import azkaban.db.SQLTransaction;
import azkaban.user.User;
import cloudflow.models.Space;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.commons.dbutils.ResultSetHandler;
import org.joda.time.DateTime;

@Singleton
public class SpaceDao {

  private DatabaseOperator databaseOperator;

  private SpaceSuperUserDao spaceSuperUserDao;

  static String INSERT_SPACE =
      "insert into  space ( name ,  description ,  created_on ,  created_by ,  modified_on ,  modified_by ) "
          + "values (?, ?, ?, ?, ?, ?)";

  @Inject
  public SpaceDao(DatabaseOperator operator, SpaceSuperUserDao spaceSuperUserDao) {
    this.databaseOperator = operator;
    this.spaceSuperUserDao = spaceSuperUserDao;
  }

  /* not the best code possible
     This is just an initial draft
     returns the space id
   */
  public int createSpace(Space space, User user) {
    final SQLTransaction<Long> insertAndGetSpaceId = transOperator -> {
      String currentTime = DateTime.now().toLocalDateTime().toString();
      transOperator.update(INSERT_SPACE, space.getName(), space.getDescription(),
          currentTime, user.getUserId(), currentTime, user.getUserId());
      transOperator.getConnection().commit();
      return transOperator.getLastInsertId();
    };

    int spaceId = 0;
    try {
      /* what will happen if there is a partial failure in
         any of the below statements?
         Ideally all should happen in a transaction */
      spaceId = databaseOperator.transaction(insertAndGetSpaceId).intValue();
      spaceSuperUserDao.addAdmins(spaceId, space.getAdmins());
      spaceSuperUserDao.addWatchers(spaceId, space.getWatchers());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return spaceId;
  }

  public Optional<Space> getSpace(int spaceId) {
    List<Space> spaces = new ArrayList<>();
    FetchSpaceHandler fetchSpaceHandler = new FetchSpaceHandler();
    try {
      spaces = databaseOperator.query(FetchSpaceHandler.FETCH_SPACE_WITH_ID, fetchSpaceHandler,
          spaceId);
      for(Space s : spaces) {
        s.setAdmins(spaceSuperUserDao.findAdminsBySpaceId(s.getId()));
        s.setWatchers(spaceSuperUserDao.findWatchersBySpaceId(s.getId()));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return spaces.isEmpty() ? Optional.empty() : Optional.of(spaces.get(0));
  }

  public List<Space> getAllSpaces() {
    List<Space> spaces = new ArrayList<>();
    FetchSpaceHandler fetchSpaceHandler = new FetchSpaceHandler();
    try {
      spaces = databaseOperator.query(FetchSpaceHandler.FETCH_ALL_SPACES, fetchSpaceHandler);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return spaces;
  }

  public static class FetchSpaceHandler implements ResultSetHandler<List<Space>> {

    static String FETCH_SPACE_WITH_ID =
        "SELECT id, name, description FROM space WHERE id = ?";
    static String FETCH_ALL_SPACES =
        "SELECT id, name, description from space";

    @Override
    public List<Space> handle(ResultSet rs) throws SQLException {

      if (!rs.next()) {
        return Collections.emptyList();
      }
      List<Space> spaces = new ArrayList<>();
      do {
        int id = rs.getInt(1);
        String spaceName = rs.getString(2);
        String description = rs.getString(3);
        Space currentSpace = new Space();
        currentSpace.setId(id);
        currentSpace.setName(spaceName);
        currentSpace.setDescription(description);
        spaces.add(currentSpace);
      } while(rs.next());

      return spaces;
    }
  }
}
