package iblutha.demo.eip.littleshop.backend.processors;

import iblutha.demo.eip.littleshop.api.commands.AddUser;
import iblutha.demo.eip.littleshop.api.events.AddUserFailed;
import iblutha.demo.eip.littleshop.backend.domain.BackendAddUserFailed;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by tibo on 06/12/2015.
 */
public class AddUserValidationExceptionProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        AddUser addUser = exchange.getIn().getBody(AddUser.class);
        String error = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class).getMessage();

        exchange.getIn().setBody(
                BackendAddUserFailed.copyBuilder(addUser)
                        .reason(error)
                        .build(),
                AddUserFailed.class);
    }
}
