package iblutha.demo.routes;

import com.google.gson.JsonParseException;
import iblutha.demo.domain.User;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.rest.RestBindingMode;

import javax.xml.bind.ValidationException;

/**
 * Created by tibo on 14/11/2015.
 */
public class UserRouteBuilder extends ExtendedRouteBuilder {
    public static final String RESTLET = "restlet";
    public static final int PORT = 8000;

    public static final String USER_ENDPOINT = "/user/";

    public static final String APPLICATION_JSON = "application/json";

    @Override
    public void configure() throws Exception {

        restConfiguration().component("spark-rest").bindingMode(RestBindingMode.json)//.host("localhost").port(PORT)
                // and output using pretty print
                .dataFormatProperty("prettyPrint", "true");

//        onException(JsonParseException.class, ValidationException.class)
//                .handled(true)
//                .setHeader(Exchange.HTTP_RESPONSE_CODE).constant("404")
//                .setHeader(Exchange.CONTENT_TYPE).constant("text/plain")
//                .transform().simple("${exception.message}")
//                .end();
//
//        onException(Exception.class)
//                .handled(true)
//                .setHeader(Exchange.HTTP_RESPONSE_CODE).constant("500")
//                .setHeader(Exchange.CONTENT_TYPE).constant("text/plain")
//                .log("${exception.message}")
//                .transform().simple("${exception.message}")
//                .end();


        rest(USER_ENDPOINT)
                .get("test")
                .route()
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                log.info("yop : ${body}");
                            }
                        })
//                .to(validator(Song.class))
//                .to("bean:ManageEquipmentActivationDelegateService?method=activateServicesForVoocorderSwap")
                .setHeader(Exchange.HTTP_RESPONSE_CODE).constant("200")
                .setBody(constant("YOP YOP"))
                .endRest()
        ;
    }
}
