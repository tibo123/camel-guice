package iblutha.demo.eip.littleshop.backend;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import iblutha.demo.eip.littleshop.api.commands.AddUser;
import iblutha.demo.eip.littleshop.api.commands.Command;
import iblutha.demo.eip.littleshop.api.commands.ShoppingHistory;
import iblutha.demo.eip.littleshop.api.events.Event;
import iblutha.demo.eip.littleshop.api.events.ShoppingHistoryReturned;
import iblutha.demo.eip.littleshop.api.events.UserAdded;
import iblutha.demo.eip.littleshop.backend.domain.*;
import iblutha.demo.eip.littleshop.backend.processors.AddUserParsingExceptionProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;

import static org.apache.camel.builder.PredicateBuilder.and;

/**
         * Created by tibo on 29/11/2015.
 */
@Named("BackendUserRouteBuilder")
public class BackendUserRouteBuilder extends RouteBuilder {

    private final static Logger LOGGER = LoggerFactory.getLogger(BackendUserRouteBuilder.class);

    private String addUserConsumerUri;
    private String addUserDlqUri;
    private String userAddedProducerUri;
    private String shoppingHistoryConsumerUri;
    private String shoppingHistoryDlqUri;
    private String shoppingHistorySuccessProducerUri;

    private AddUserParsingExceptionProcessor addUserParsingExceptionProcessor;

    @Inject
    public BackendUserRouteBuilder(@Named("addUser.consumer.uri")String addUserConsumerUri,
                                   @Named("addUser.dlq.uri")String addUserDlqUri,
                                   @Named("userAdded.producer.uri")String userAddedProducerUri,
                                   @Named("shoppingHistory.consumer.uri")String shoppingHistoryConsumerUri,
                                   @Named("shoppingHistory.dlq.uri")String shoppingHistoryDlqUri,
                                   @Named("shoppingHistorySuccess.producer.uri")String shoppingHistorySuccessProducerUri,
                                   AddUserParsingExceptionProcessor addUserParsingExceptionProcessor) {
        this.addUserConsumerUri = addUserConsumerUri;
        this.addUserDlqUri = addUserDlqUri;
        this.userAddedProducerUri = userAddedProducerUri;
        this.shoppingHistoryConsumerUri = shoppingHistoryConsumerUri;
        this.shoppingHistoryDlqUri = shoppingHistoryDlqUri;
        this.shoppingHistorySuccessProducerUri = shoppingHistorySuccessProducerUri;
        this.addUserParsingExceptionProcessor = addUserParsingExceptionProcessor;
    }

    @Override
    public void configure() throws Exception {

        onException(JsonParseException.class, UnrecognizedPropertyException.class)
                .routeId("addUser-backend-unmarshalling-failed-route")
                .log(LoggingLevel.TRACE, LOGGER, "addUser-backend-unmarshalling-failed-route START")
                .handled(true)
                .log(LoggingLevel.ERROR, LOGGER, "Received unparsable command ${body}")
                .process(addUserParsingExceptionProcessor)
                .marshal().json(JsonLibrary.Jackson, BackendAddUserFailed.class)
                .to(addUserDlqUri)
                .end();

        onException(BeanValidationException.class)
                .routeId("addUser-backend-validation-failed-route")
                .log(LoggingLevel.TRACE, LOGGER, "addUser-backend-validation-failed-route START")
                .handled(true)
                .log(LoggingLevel.ERROR, LOGGER, "Received invalid command ${body}")
                .marshal().json(JsonLibrary.Jackson, BackendAddUserFailed.class)
                .to(addUserDlqUri)
                .end();

        onException(Exception.class)
                .routeId("addUser-backend-exception-failed-route")
                .log(LoggingLevel.TRACE, LOGGER, "addUser-backend-exception-failed-route START")
                .log(LoggingLevel.ERROR, LOGGER, "Could not send InvestigateBACreationTimeout command to bpm : ${body}")
                .end();


        from(addUserConsumerUri)
                .routeId("addUser-backend-route")
                .log(LoggingLevel.TRACE, LOGGER, "addUser-backend-route START")
                .log(LoggingLevel.DEBUG, LOGGER, "backend addUSer - BODY : ${body}")
                .filter(
                        and(
                                header(Command.TYPE).isEqualTo(AddUser.TYPE),
                                header(Command.VERSION).isEqualTo(AddUser.VERSION)))
                .unmarshal().json(JsonLibrary.Jackson, BackendAddUser.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        AddUser user = exchange.getIn().getBody(BackendAddUser.class);
                        exchange.getIn().setBody(
                                BackendUserAdded.newBuilder()
                                        .response(new StringBuilder("User : ")
                                                .append(user.getFirstname()).append(" ").append(user.getLastname()).append(" has been added.")
                                                .toString())
                                        .build(),
                                UserAdded.class);
                    }
                })
                .setHeader(Event.TYPE, constant(UserAdded.TYPE))
                .setHeader(Event.VERSION, constant(UserAdded.VERSION))
                .to(userAddedProducerUri)
        ;

        from(shoppingHistoryConsumerUri)
                .routeId("shoppingHistory-backend-route")
                .log(LoggingLevel.DEBUG, LOGGER, "backend shoppingHistory - BODY : ${body}")
                .filter(
                        and(
                                header(Command.TYPE).isEqualTo(ShoppingHistory.TYPE),
                                header(Command.VERSION).isEqualTo(ShoppingHistory.VERSION)))
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setBody(
                                BackendShoppingHistoryReturned.newBuilder()
                                        .shoppingItems(Arrays.asList(
                                                BackendShoppingItem.newBuilder()
                                                        .productName("productName1")
                                                        .purchaseDate("purchaseDate1")
                                                        .build(),
                                                BackendShoppingItem.newBuilder()
                                                        .productName("productName2")
                                                        .purchaseDate("purchaseDate2")
                                                        .build()))
                                        .build(),
                                ShoppingHistoryReturned.class);
                    }
                })
                .setHeader(Event.TYPE, constant(ShoppingHistoryReturned.TYPE))
                .setHeader(Event.VERSION, constant(ShoppingHistoryReturned.VERSION))
                .marshal().json(JsonLibrary.Jackson, ShoppingHistoryReturned.class)
                .log(LoggingLevel.DEBUG,LOGGER,"SEND ShoppingHistoryReturned - BODY : ${body}")
                .inOnly(shoppingHistorySuccessProducerUri)
        ;
    }
}
