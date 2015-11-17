package iblutha.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by tibo on 14/11/2015.
 */
public class Starter {

    private static Logger logger = LoggerFactory.getLogger(Starter.class);

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
        System.out.println("Press Ctrl-C to quit");
    }
}
