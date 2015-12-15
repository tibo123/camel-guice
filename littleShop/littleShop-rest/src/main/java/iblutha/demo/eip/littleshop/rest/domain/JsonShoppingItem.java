package iblutha.demo.eip.littleshop.rest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iblutha.demo.eip.littleshop.api.domain.ShoppingItem;

import javax.validation.constraints.NotNull;

/**
 * Created by tibo on 03/12/2015.
 */
@JsonDeserialize(builder = JsonShoppingItem.Builder.class)
public class JsonShoppingItem implements ShoppingItem {
    @NotNull
    private String productName;
    @NotNull
    private String purchaseDate;

    private JsonShoppingItem(Builder builder) {
        this.productName = builder.productName;
        this.purchaseDate = builder.purchaseDate;
    }

    public static Builder newBuilder() { return new Builder();}

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String productName;
        private String purchaseDate;

        public Builder productName(String productName){
            this.productName = productName;
            return this;
        }
        public Builder purchaseDate(String purchaseDate){
            this.purchaseDate = purchaseDate;
            return this;
        }
        public JsonShoppingItem build() {
            return new JsonShoppingItem(this);
        }
    }

    public String toString() {
        return new StringBuilder("JsonShoppingItem [")
                .append("productName : ").append(productName).append("")
                .append("purchaseDate : ").append(purchaseDate).append("]")
                .toString();
    }
    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public String getPurchaseDate() {
        return purchaseDate;
    }
}
