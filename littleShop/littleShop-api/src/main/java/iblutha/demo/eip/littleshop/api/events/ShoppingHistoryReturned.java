package iblutha.demo.eip.littleshop.api.events;

import iblutha.demo.eip.littleshop.api.domain.ShoppingItem;

import java.util.List;

/**
 * Created by tibo on 29/11/2015.
 */
public interface ShoppingHistoryReturned extends Event {
    String TYPE = "ShoppingHistoryReturned_Event";
    String VERSION = "1";

    List<ShoppingItem> getShoppingItems();
//    ShoppingItem getShoppingItem();
}
