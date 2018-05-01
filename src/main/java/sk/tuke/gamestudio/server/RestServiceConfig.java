package sk.tuke.gamestudio.server;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/rest")
public class RestServiceConfig extends ResourceConfig {
    public RestServiceConfig() {
        packages("sk.tuke.gamestudio.server");
    }

}
