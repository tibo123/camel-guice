package iblutha.demo.eip.littleshop.api.domain;

import java.io.Serializable;

/**
 * Created by tibo on 29/11/2015.
 */
public interface ShoppingItem extends Serializable {

    String getProductName();
    String getPurchaseDate();
}
