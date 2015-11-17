package iblutha.demo;

import com.google.inject.Provides;
import iblutha.demo.routes.UserRouteBuilder;
import org.apache.camel.component.sparkrest.ServletSparkApplication;
import org.apache.camel.guice.CamelModuleWithMatchingRoutes;
import org.apache.camel.guice.jndi.JndiBind;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * Created by tibo on 14/11/2015.
 */
public class StartModule  extends CamelModuleWithMatchingRoutes {
    @Override
    protected void configure() {
        super.configure();
        bind(UserRouteBuilder.class);
    }
}
