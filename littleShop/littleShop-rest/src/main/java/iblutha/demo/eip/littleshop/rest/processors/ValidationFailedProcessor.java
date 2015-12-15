package iblutha.demo.eip.littleshop.rest.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.inject.Named;

/**
 * Created by tibo on 29/11/2015.
 */
@Named("ValidationFailedProcessor")
public class ValidationFailedProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

    }
}
