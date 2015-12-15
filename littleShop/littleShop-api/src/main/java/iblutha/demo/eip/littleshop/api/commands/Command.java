package iblutha.demo.eip.littleshop.api.commands;

import java.io.Serializable;

/**
 * Created by tibo on 29/11/2015.
 */
public interface Command extends Serializable{
    String TYPE = "Command_Type";
    String VERSION = "Command_Version";
}
