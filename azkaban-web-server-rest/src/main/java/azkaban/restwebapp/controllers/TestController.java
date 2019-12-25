package azkaban.restwebapp.controllers;

import azkaban.restwebapp.daos.SpaceSuperUserDao;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

  @Autowired
  SpaceSuperUserDao spaceSuperUserDao;

  @RequestMapping("/admin/{user}")
  public List<String> admin(@PathVariable("user") String userId) {
     spaceSuperUserDao.addAdmins(1, Collections.singletonList(userId));
     return spaceSuperUserDao.findAdminsBySpaceId(1);
  }

  @RequestMapping("/watcher/{user}")
  public List<String> watcher(@PathVariable("user") String userId) {
    spaceSuperUserDao.addWatchers(1, Collections.singletonList(userId));
    return spaceSuperUserDao.findWatchersBySpaceId(1);
  }
}
