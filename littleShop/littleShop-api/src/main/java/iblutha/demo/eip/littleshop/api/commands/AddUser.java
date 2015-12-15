package iblutha.demo.eip.littleshop.api.commands;

import iblutha.demo.eip.littleshop.api.domain.User;

/**
 * Created by tibo on 29/11/2015.
 */
public interface AddUser extends User, Command {
    String TYPE = "AddUser_Command";
    String VERSION = "1";
}
