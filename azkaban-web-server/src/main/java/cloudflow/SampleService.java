package cloudflow;


import javax.inject.Singleton;

/* Just a service added to verify basic things */
@Singleton
public class SampleService {

  public SampleService() {

  }
  public String test() {
    return "Hello";
  }
}
