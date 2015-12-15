package iblutha.demo.eip.littleshop.backend.processors;

import iblutha.demo.eip.littleshop.api.events.AddUserFailed;
import iblutha.demo.eip.littleshop.backend.domain.BackendAddUserFailed;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.inject.Named;

/**
 * Created by tibo on 06/12/2015.
 */
@Named("AddUserParsingExceptionProcessor")
public class AddUserParsingExceptionProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String error = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class).getMessage();

        exchange.getIn().setBody(
                BackendAddUserFailed.newBuilder()
                        .reason(error)
                        .build(),
                AddUserFailed.class);
    }
}
