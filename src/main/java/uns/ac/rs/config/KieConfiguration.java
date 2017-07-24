package uns.ac.rs.config;

/**
 * Created by Nikola Dakic on 7/24/17.
 */

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KieConfiguration {

    @Bean
    public KieContainer kieContainer() {
        return KieServices.Factory.get().getKieClasspathContainer();
    }
}
