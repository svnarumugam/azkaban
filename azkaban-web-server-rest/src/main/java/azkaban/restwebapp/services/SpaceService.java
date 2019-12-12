package azkaban.restwebapp.services;

import azkaban.db.DatabaseOperator;
import azkaban.db.EncodingType;
import azkaban.db.SQLTransaction;
import azkaban.project.JdbcProjectHandlerSet.ProjectResultHandler;
import azkaban.project.Project;
import azkaban.project.ProjectManagerException;
import azkaban.restwebapp.models.Space;
import azkaban.user.User;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.springframework.beans.factory.annotation.Autowired;

@Singleton
public class SpaceService {

  private final EncodingType defaultEncodingType = EncodingType.GZIP;

  @Inject
  public SpaceService(DatabaseOperator dbOperator) {
    this.dbOperator = dbOperator;
  }

  public Space create() {
    return new Space(1, "a", "b");
  }

  private final DatabaseOperator dbOperator;

  public synchronized Project createNewProject(final String name, final String description,
      final User creator)
      throws ProjectManagerException {
    final ProjectResultHandler handler = new ProjectResultHandler();

    // Check if the same project name exists.
    try {
      final List<Project> projects = this.dbOperator
          .query(ProjectResultHandler.SELECT_ACTIVE_PROJECT_BY_NAME, handler, name);
      if (!projects.isEmpty()) {
        throw new ProjectManagerException(
            "Active project with name " + name + " already exists in db.");
      }
    } catch (final SQLException ex) {
      //logger.error(ex);
      throw new ProjectManagerException("Checking for existing project failed. " + name, ex);
    }

    final String INSERT_PROJECT =
        "INSERT INTO projects ( name, active, modified_time, create_time, version, last_modified_by, description, enc_type, settings_blob) values (?,?,?,?,?,?,?,?,?)";
    final SQLTransaction<Integer> insertProject = transOperator -> {
      final long time = System.currentTimeMillis();
      return transOperator
          .update(INSERT_PROJECT, name, true, time, time, null, creator.getUserId(), description,
              this.defaultEncodingType.getNumVal(), null);
    };

    // Insert project
    try {
      final int numRowsInserted = this.dbOperator.transaction(insertProject);
      if (numRowsInserted == 0) {
        throw new ProjectManagerException("No projects have been inserted.");
      }
    } catch (final SQLException ex) {
      //logger.error(INSERT_PROJECT + " failed.", ex);
      throw new ProjectManagerException("Insert project" + name + " for existing project failed. ",
          ex);
    }
    return new Project(200, "test");
  }
}
