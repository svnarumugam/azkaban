package cloudflow.services;

import azkaban.project.AzkabanProjectLoader;
import azkaban.project.Project;
import azkaban.project.ProjectFileHandler;
import azkaban.project.ProjectLoader;
import azkaban.user.User;
import azkaban.utils.Props;
import cloudflow.error.CloudFlowException;
import cloudflow.error.CloudFlowValidationException;
import com.mysql.jdbc.StringUtils;
import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UploadServiceImpl implements UploadService {

  /* It's a DAO class but named differently, so calling the variable as DAO */
  private final ProjectLoader projectLoader;
  private final AzkabanProjectLoader azkabanProjectLoader;

  @Inject
  public UploadServiceImpl(final ProjectLoader projectLoader,
      AzkabanProjectLoader azkabanProjectLoader) {
    this.projectLoader = projectLoader;
    this.azkabanProjectLoader = azkabanProjectLoader;
  }

  @Override
  public int addProjectMetadata(int projectId, User user) {

    int latestVersion = projectLoader.getLatestProjectVersion(projectId);
    int nextVersion = latestVersion + 1;
    ProjectFileHandler projectFileHandler = projectLoader
        .fetchProjectMetaData(projectId, latestVersion);
    /* there shouldn't be any entry with emtpy file if the last upload is complete */
    if (projectFileHandler != null && StringUtils.isNullOrEmpty(projectFileHandler.getFileName())) {
      throw new CloudFlowValidationException("An upload is already in progress with version " + latestVersion);
    }
    try {

      projectLoader.addProjectVersion(projectId, nextVersion, null, null,
          user.getUserId(), null, null);
    } catch(Exception e) {
      throw new CloudFlowException("Unable to create new project version");
    }
    // get user context as parameter here */
    // projectLoader.addProjectVersion();
    return nextVersion;
  }

  @Override
  public void uploadProject(int projectId, int version, File file, User user) {

    ProjectFileHandler projectFileHandler = projectLoader
        .fetchProjectMetaData(projectId, version);

    if (projectFileHandler == null || !StringUtils.isNullOrEmpty(projectFileHandler.getFileName())) {
      throw new  CloudFlowException("Upload metadata is not created");
    }

    Project project = projectLoader.fetchProjectById(projectId);

    try {
      azkabanProjectLoader.uploadProjectV2(project, version, file, "zip", user, new Props());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public int addFlowMetadata(int flowId) {
    // will be implemented in the next PR
    return 0;
  }

  @Override
  public void uploadFlow(int flow, int projectVersion, File file) {
    // will be implemented in the next PR
  }
}
