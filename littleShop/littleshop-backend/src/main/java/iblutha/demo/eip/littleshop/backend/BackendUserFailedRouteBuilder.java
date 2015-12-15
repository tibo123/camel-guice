package iblutha.demo.eip.littleshop.backend;

import iblutha.demo.eip.littleshop.api.events.AddUserFailed;
import iblutha.demo.eip.littleshop.api.events.Event;
import iblutha.demo.eip.littleshop.api.events.ShoppingHistoryFailed;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by tibo on 29/11/2015.
 */
@Named("BackendUserFailedRouteBuilder")
public class BackendUserFailedRouteBuilder extends RouteBuilder {

    private final static Logger LOGGER = LoggerFactory.getLogger(BackendUserFailedRouteBuilder.class);

    private String addUserDlqUri;
    private String addUserFailedProducerUri;
    private String shoppingHistoryDlqUri;
    private String shoppingHistoryFailedProducerUri;

    @Inject
    public BackendUserFailedRouteBuilder(@Named("addUser.dlq.uri")String addUserDlqUri,
                                         @Named("addUserFailed.producer.uri") String addUserFailedProducerUri,
                                         @Named("shoppingHistory.dlq.uri")String shoppingHistoryDlqUri,
                                         @Named("shoppingHistoryFailed.producer.uri")String shoppingHistoryFailedProducerUri) {
        this.addUserDlqUri = addUserDlqUri;
        this.addUserFailedProducerUri = addUserFailedProducerUri;
        this.shoppingHistoryDlqUri = shoppingHistoryDlqUri;
        this.shoppingHistoryFailedProducerUri = shoppingHistoryFailedProducerUri;
    }

    @Override
    public void configure() throws Exception {

        from(addUserDlqUri)
                .routeId("addUserDlqUri - route")
                .log(LoggingLevel.DEBUG, LOGGER, "addUserDlq BODY : ${body}")
                .setHeader(Event.TYPE).constant(AddUserFailed.TYPE)
                .setHeader(Event.VERSION).constant(AddUserFailed.VERSION)
                .to(addUserFailedProducerUri)
        ;

        from(shoppingHistoryDlqUri)
                .routeId("shoppingHistoryDlqUri - route")
                .log(LoggingLevel.DEBUG, LOGGER, "shoppingHistoryDlq BODY : ${body}")
                .setHeader(Event.TYPE).constant(ShoppingHistoryFailed.TYPE)
                .setHeader(Event.VERSION).constant(ShoppingHistoryFailed.VERSION)
                .to(shoppingHistoryFailedProducerUri)
        ;
    }
}
