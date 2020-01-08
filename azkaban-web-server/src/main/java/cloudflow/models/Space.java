package cloudflow.models;

import java.util.List;
import java.util.List;
import java.util.Objects;

public class Space {
  private int id;
  private String name;
  private String description;
  private List<String> admins;
  private List<String> watchers;
  public Space() {
  }

  public List<String> getAdmins() {
    return admins;
  }

  public void setAdmins(List<String> admins) {
    this.admins = admins;
  }

  public List<String> getWatchers() {
    return watchers;
  }

  public void setWatchers(List<String> watchers) {
    this.watchers = watchers;
  }

  public Space(int id, String name, String description, List<String> admins,
      List<String> watchers) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.admins = admins;
    this.watchers = watchers;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Space space = (Space) o;
    return id == space.id &&
        name.equals(space.name) &&
        description.equals(space.description) &&
        admins.equals(space.admins) &&
        watchers.equals(space.watchers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, admins, watchers);
  }
}

