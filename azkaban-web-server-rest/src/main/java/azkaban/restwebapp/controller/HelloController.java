package azkaban.restwebapp.controller;

import azkaban.project.Project;
import azkaban.project.ProjectManager;
import azkaban.spi.Storage;
import azkaban.webapp.StatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

  @Autowired
  StatusService statusService;

  @Autowired
  ProjectManager projectManager;

  @Autowired
  Storage storage;

  @RequestMapping("/hello")
  public List<Project> greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return projectManager.getProjects();
  }
}
