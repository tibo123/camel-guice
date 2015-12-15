package iblutha.demo.eip.littleshop.backend.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iblutha.demo.eip.littleshop.api.domain.ShoppingItem;

import javax.validation.constraints.NotNull;

/**
 * Created by tibo on 29/11/2015.
 */
@JsonDeserialize(builder = BackendShoppingItem.Builder.class)
public class BackendShoppingItem implements ShoppingItem {
    @NotNull
    private String productName;
    @NotNull
    private String purchaseDate;

    private BackendShoppingItem(Builder builder) {
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
        public BackendShoppingItem build() {
            return new BackendShoppingItem(this);
        }
    }

    public String toString() {
        return new StringBuilder("BackendShoppingItem [")
                .append("productName : ").append(productName).append(", ")
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
