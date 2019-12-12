package azkaban.restwebapp.models;


import java.util.Objects;

public class Space {
  private int id;
  private String name;
  private String description;
  public Space() {
  }

  public Space(int id, String name, String description) {
    this.id = id;
    this.name = name;
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
    Space that = (Space) o;
    return id == that.id &&
        Objects.equals(name, that.name) &&
        Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description);
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
}
