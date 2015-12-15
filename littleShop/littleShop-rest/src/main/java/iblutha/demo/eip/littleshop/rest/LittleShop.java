package iblutha.demo.eip.littleshop.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by tibo on 29/11/2015.
 */
public class LittleShop {

    private static Logger logger = LoggerFactory.getLogger(LittleShop.class);

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
