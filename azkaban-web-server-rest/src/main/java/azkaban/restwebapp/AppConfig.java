package azkaban.restwebapp;

import azkaban.AzkabanCommonModule;
import azkaban.AzkabanCoreModule;
import azkaban.utils.Props;
import azkaban.webapp.AzkabanWebServer;
import azkaban.webapp.AzkabanWebServerModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.guice.annotation.EnableGuiceModules;

/*

  The existing modules including AzkabanCoreModule, AzkananCommonModule
  are Guice modules. We need to inject the objects created
  in these modules in the spring run time. That is why we create these
  modules as beans. The annotation EnableGuiceModules helps us to pass
  the guice created modules/objects to the spring run time.

  For now, haven't found a way to cleanly pass the command line argument
  so hard coded the config path. will be modified soon.
 */
@EnableGuiceModules
@Configuration
public class AppConfig {

  private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
  public AppConfig() {}

  @Bean
  public AzkabanCoreModule azkabanCoreModule() {
    Props p = AzkabanWebServer.loadProps(new String[] {"-conf", "/Users/sarumuga/workspace"
        + "/azkaban/conf"});
    //ApplicationArguments applicationArguments =
    return new AzkabanCoreModule(p);
  }

  @Bean
  public AzkabanCommonModule azkabanCommonModule() {
    Props p = AzkabanWebServer.loadProps(new String[] {"-conf", "/Users/sarumuga/workspace"
        + "/azkaban/conf"});
    return new AzkabanCommonModule(p);
  }

  @Bean
  public AzkabanWebServerModule azkabanWebServerModule() {
    Props p = AzkabanWebServer.loadProps(new String[] {"-conf", "/Users/sarumuga/workspace"
        + "/azkaban/conf"});
    return new AzkabanWebServerModule(p);
  }
}
