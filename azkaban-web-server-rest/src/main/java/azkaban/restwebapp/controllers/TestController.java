package azkaban.restwebapp.controllers;

import azkaban.project.Project;
import azkaban.project.ProjectLoader;
import azkaban.project.ProjectManager;
import azkaban.spi.Storage;
import azkaban.webapp.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

  @Autowired
  StatusService statusService;

  @Autowired
  ProjectManager projectManager;

  @Autowired
  Storage storage;

  @Autowired
  ProjectLoader projectLoader;

  @RequestMapping("/storage")
  public Boolean greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return storage != null;
    //return projectManager.getProjects();
  }

  @RequestMapping("/projects/{projectId}")
  public Project jdbcObjectAccess(@PathVariable("projectId") Integer projectId) {
    return projectLoader.fetchProjectById(projectId);
  }
}
