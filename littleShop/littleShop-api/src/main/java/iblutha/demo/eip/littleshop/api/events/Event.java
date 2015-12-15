package iblutha.demo.eip.littleshop.api.events;

import java.io.Serializable;

/**
 * Created by tibo on 28/11/2015.
 */
public interface Event extends Serializable {
    String TYPE = "Event_Type";
    String VERSION = "Event_Version";
}
