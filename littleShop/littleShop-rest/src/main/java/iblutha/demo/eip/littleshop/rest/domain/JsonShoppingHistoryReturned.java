package iblutha.demo.eip.littleshop.rest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iblutha.demo.eip.littleshop.api.domain.ShoppingItem;
import iblutha.demo.eip.littleshop.api.events.ShoppingHistoryReturned;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by tibo on 29/11/2015.
 */
@JsonDeserialize(builder = JsonShoppingHistoryReturned.Builder.class)
public class JsonShoppingHistoryReturned implements ShoppingHistoryReturned {
    @NotNull
    private List<ShoppingItem> shoppingItems;

    private JsonShoppingHistoryReturned(Builder builder) {
        this.shoppingItems = builder.shoppingItems;
    }

    public static Builder newBuilder() { return new Builder();}

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        @JsonDeserialize(contentAs=JsonShoppingItem.class)
        private List<ShoppingItem> shoppingItems;

        public Builder shoppingItems(List<ShoppingItem> shoppingItems){
            this.shoppingItems = shoppingItems;
            return this;
        }

        public JsonShoppingHistoryReturned build() {
            return new JsonShoppingHistoryReturned(this);
        }
    }

    public String toString() {
        return new StringBuilder("JsonShoppingHistoryReturned [")
                .append("shoppingItems : ").append(shoppingItems).append("]")
                .toString();
    }

    @Override
    public List<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }
}
