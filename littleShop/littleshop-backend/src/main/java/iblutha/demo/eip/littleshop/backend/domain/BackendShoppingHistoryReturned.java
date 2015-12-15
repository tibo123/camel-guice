package iblutha.demo.eip.littleshop.backend.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iblutha.demo.eip.littleshop.api.domain.ShoppingItem;
import iblutha.demo.eip.littleshop.api.events.ShoppingHistoryReturned;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by tibo on 29/11/2015.
 */
@JsonDeserialize(builder = BackendShoppingHistoryReturned.Builder.class)
public class BackendShoppingHistoryReturned implements ShoppingHistoryReturned {
    @NotNull
    @JsonDeserialize(builder = BackendShoppingItem.Builder.class)
    private List<ShoppingItem> shoppingItems;

    private BackendShoppingHistoryReturned(Builder builder) {
        this.shoppingItems = builder.shoppingItems;
    }

    public static Builder newBuilder() { return new Builder();}

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        @JsonDeserialize(builder = BackendShoppingItem.Builder.class)
        private List<ShoppingItem> shoppingItems;

        public Builder shoppingItems(List<ShoppingItem> shoppingItems){
            this.shoppingItems = shoppingItems;
            return this;
        }
        public BackendShoppingHistoryReturned build() {
            return new BackendShoppingHistoryReturned(this);
        }
    }

    public String toString() {
        return new StringBuilder("BackendShoppingHistoryReturned [")
                .append("shoppingItems : ").append(shoppingItems).append("]")
                .toString();
    }
    @Override
    public List<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }
}
