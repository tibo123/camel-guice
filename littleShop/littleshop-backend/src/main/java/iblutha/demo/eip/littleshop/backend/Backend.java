package iblutha.demo.eip.littleshop.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by tibo on 03/12/2015.
 */
public class Backend {

    private static Logger logger = LoggerFactory.getLogger(Backend.class);

    public static void main(String[] args) {
        try {
            // Create the Camel context with Guice
            InitialContext context = new InitialContext();
            // Wait forever
            Thread.currentThread().join();
        } catch (NamingException | InterruptedException ne) {
            //TODO add log conf
            logger.error(ne.getMessage(), ne);
        }
    }
}
