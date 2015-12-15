package iblutha.demo.eip.littleshop.rest.routes;

import iblutha.demo.eip.littleshop.api.events.AddUserFailed;
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
@Named("RestUserFailedRouteBuilder")
public class RestUserFailedRouteBuilder extends RouteBuilder {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestUserFailedRouteBuilder.class);

    private String addUserDlqUri;
    private String addUserFailedProducerUri;
    private String shoppingHistoryDlqUri;
    private String shoppingHistoryFailedProducerUri;

    @Inject
    public RestUserFailedRouteBuilder(@Named("addUser.dlq.uri")String addUserDlqUri,
                                      @Named("addUserFailed.producer.uri") String addUserFailedProducerUri,
                                      @Named("shoppingHistory.dlq.uri")String shoppingHistoryDlqUri,
                                      @Named("shoppingHistoryFailed.producer.uri")String shoppingHistoryFailedProducerUri) {
        this.addUserDlqUri = addUserDlqUri;
        this.shoppingHistoryDlqUri = shoppingHistoryDlqUri;
        this.addUserFailedProducerUri = addUserFailedProducerUri;
        this.shoppingHistoryFailedProducerUri = shoppingHistoryFailedProducerUri;
    }

    @Override
    public void configure() throws Exception {

        from(addUserDlqUri)
                .routeId("addUserFailed - route")
                .log(LoggingLevel.DEBUG, LOGGER, "addUserFailed BODY : ${body}")
                .unmarshal().json(JsonLibrary.Jackson, AddUserFailed.class)
                .to(addUserFailedProducerUri)
        ;

        from(shoppingHistoryDlqUri)
                .routeId("shoppingHistoryFailed - route")
                .log(LoggingLevel.DEBUG, LOGGER, "shoppingHistoryFailed BODY : ${body}")
                .to(shoppingHistoryFailedProducerUri)
        ;
    }
}
