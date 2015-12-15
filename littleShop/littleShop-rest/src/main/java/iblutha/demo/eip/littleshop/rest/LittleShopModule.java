package iblutha.demo.eip.littleshop.rest;

import com.google.inject.Provides;
import iblutha.demo.eip.littleshop.rest.routes.RestUserFailedRouteBuilder;
import iblutha.demo.eip.littleshop.backend.BackendModule;
import iblutha.demo.eip.littleshop.rest.routes.RestUserRouteBuilder;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.guice.CamelModuleWithMatchingRoutes;
import org.apache.camel.guice.jndi.JndiBind;

import javax.inject.Named;

/**
 * Created by tibo on 29/11/2015.
 */
public class LittleShopModule  extends CamelModuleWithMatchingRoutes {

    @Override
    protected void configure() {
        super.configure();

        bind(RestUserRouteBuilder.class);
        bind(RestUserFailedRouteBuilder.class);
    }

    @Provides
    @JndiBind("jms")
    JmsComponent jms(@Named("activemq.brokerURL") String brokerUrl) {
        return JmsComponent.jmsComponent(new ActiveMQConnectionFactory(brokerUrl));
    }
}
