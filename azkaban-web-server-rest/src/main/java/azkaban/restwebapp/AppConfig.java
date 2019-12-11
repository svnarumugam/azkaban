package azkaban.restwebapp;

import azkaban.AzkabanCommonModule;
import azkaban.AzkabanCoreModule;
import azkaban.utils.Props;
import azkaban.webapp.AzkabanWebServer;
import azkaban.webapp.AzkabanWebServerModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.guice.annotation.EnableGuiceModules;


@EnableGuiceModules
@Configuration
public class AppConfig {

  @Bean
  public AzkabanCoreModule azkabanCoreModule() {
    Props p = AzkabanWebServer.loadProps(new String[] {"-conf", "/Users/sarumuga/workspace"
        + "/azkaban/conf"});
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
