package iblutha.demo.eip.littleshop.rest.routes;

import iblutha.demo.eip.littleshop.api.commands.AddUser;
import iblutha.demo.eip.littleshop.api.commands.Command;
import iblutha.demo.eip.littleshop.api.commands.ShoppingHistory;
import iblutha.demo.eip.littleshop.api.domain.ShoppingItem;
import iblutha.demo.eip.littleshop.api.events.ShoppingHistoryReturned;
import iblutha.demo.eip.littleshop.rest.domain.JsonAddUser;
import iblutha.demo.eip.littleshop.rest.domain.JsonShoppingHistoryReturned;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by tibo on 29/11/2015.
 */
@Named("RestUserRouteBuilder")
public class RestUserRouteBuilder extends RouteBuilder {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestUserRouteBuilder.class);

    public static final String SPARK_COMPONENT = "spark-rest";
    public static final int PORT = 8000;

    public static final String USER_ENDPOINT = "/user/";

    private String addUserProducerUri;
    private String shoppingHistoryProducerUri;
    private String shoppingHistorySuccessConsumerUri;

    @Inject
    public RestUserRouteBuilder(@Named("addUser.producer.uri")String addUserProducerUri,
                                @Named("shoppingHistory.producer.uri")String shoppingHistoryProducerUri,
                                @Named("shoppingHistorySuccess.consumer.uri")String shoppingHistorySuccessConsumerUri) {
        this.addUserProducerUri = addUserProducerUri;
        this.shoppingHistoryProducerUri = shoppingHistoryProducerUri;
        this.shoppingHistorySuccessConsumerUri = shoppingHistorySuccessConsumerUri;
    }

    @Override
    public void configure() throws Exception {

        restConfiguration().component(SPARK_COMPONENT).bindingMode(RestBindingMode.json)//.host("localhost").port(PORT)
                .dataFormatProperty("prettyPrint", "true");


        onException(Exception.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE).constant("500")
                .setHeader(Exchange.CONTENT_TYPE).constant("text/plain")
                .log("${exception.message} - BODY : [${body}]")
                .end();

        rest(USER_ENDPOINT)
                .post("add").consumes("application/json").produces("application/json")
                .route()
                    .routeId("RestUserRoute.add - route")
                    .convertBodyTo(String.class)
                    .unmarshal().json(JsonLibrary.Jackson, JsonAddUser.class)
                    .to(validator(JsonAddUser.class))
                    .setHeader(Command.TYPE).constant(AddUser.TYPE)
                    .setHeader(Command.VERSION).constant(AddUser.VERSION)
                    .log(LoggingLevel.DEBUG, LOGGER, "AddUser resquest : ${body}")
                    .inOnly(addUserProducerUri)
                    .removeHeaders("*")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE).constant("204")
                .endRest()

                .get("shoppingHistory").produces("application/json")
                .route()
                    .routeId("RestUserRoute.shoppingHistory - route")
                    .setHeader(Command.TYPE).constant(ShoppingHistory.TYPE)
                    .setHeader(Command.VERSION).constant(ShoppingHistory.VERSION)
                    .log(LoggingLevel.DEBUG, LOGGER, "shoppingHistory resquest : ${body}")
                    .to(ExchangePattern.InOut, shoppingHistoryProducerUri)
                    .convertBodyTo(String.class)
                    .log(LoggingLevel.DEBUG, LOGGER, "shoppingHistory response : ${body}")
                    .removeHeaders("*")
                .setHeader(Exchange.HTTP_RESPONSE_CODE).constant("200")
                .endRest()
        ;

        from(shoppingHistorySuccessConsumerUri)
                .routeId("Result - route")
                .log(LoggingLevel.DEBUG, LOGGER, "Result - route BODY : ${body}")
                .convertBodyTo(String.class)
                .unmarshal().json(JsonLibrary.Jackson, JsonShoppingHistoryReturned.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        List<ShoppingItem> results = exchange.getIn().getBody(ShoppingHistoryReturned.class).getShoppingItems();
                        LOGGER.debug("Shopping Item : {}", results);
                    }
                });
    }

    public String validator(final Class<?> type) {
        return MessageFormat.format("bean-validator:{0}", type.getName());
    }
}
