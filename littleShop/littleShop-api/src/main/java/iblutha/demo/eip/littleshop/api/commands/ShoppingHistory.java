package iblutha.demo.eip.littleshop.api.commands;

import iblutha.demo.eip.littleshop.api.domain.ShoppingItem;

import java.util.List;

/**
 * Created by tibo on 29/11/2015.
 */
public interface ShoppingHistory extends Command {
    String TYPE = "ShoppingHistory_Command";
    String VERSION = "1";

    List<ShoppingItem> getShoppingItems();
}
