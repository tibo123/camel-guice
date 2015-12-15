package iblutha.demo.eip.littleshop.api.events;

import iblutha.demo.eip.littleshop.api.domain.User;

/**
 * Created by tibo on 29/11/2015.
 */
public interface AddUserFailed extends User, Event {
    String TYPE = "AddUserFailed_Event";
    String VERSION = "1";

    String getReason();
}
