package iblutha.demo.eip.littleshop.backend;

import com.google.inject.Provides;
import com.google.inject.name.Names;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.guice.CamelModuleWithMatchingRoutes;
import org.apache.camel.guice.jndi.JndiBind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by tibo on 03/12/2015.
 */
public class BackendModule extends CamelModuleWithMatchingRoutes {

    private final static Logger LOGGER = LoggerFactory.getLogger(BackendModule.class);

    @Override
    protected void configure() {
        super.configure();
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("activemq.properties"));
            LOGGER.debug(properties.values().toString());
            Names.bindProperties(binder(), properties);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }


        bind(BackendUserRouteBuilder.class);
        bind(BackendUserFailedRouteBuilder.class);
    }



    @Provides
    @JndiBind("jms")
    JmsComponent jms(@Named("activemq.brokerURL") String brokerUrl) {
        return JmsComponent.jmsComponent(new ActiveMQConnectionFactory(brokerUrl));
    }
}
