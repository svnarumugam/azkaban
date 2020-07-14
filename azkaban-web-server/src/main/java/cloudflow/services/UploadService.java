package cloudflow.services;

import azkaban.user.User;
import java.io.File;

public interface UploadService {

  int addProjectMetadata(int projectId, User user);
  void uploadProject(int projectId, int version, File file, User user);

  int addFlowMetadata(int flowId);
  void uploadFlow(int flow, int projectVersion, File file);
}
