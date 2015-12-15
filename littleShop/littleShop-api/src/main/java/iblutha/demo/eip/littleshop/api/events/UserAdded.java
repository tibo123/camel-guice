package iblutha.demo.eip.littleshop.api.events;

/**
 * Created by tibo on 29/11/2015.
 */
public interface UserAdded extends Event {
    String TYPE = "UserAdded_Event";
    String VERSION = "1";

    String getResponse();
}
