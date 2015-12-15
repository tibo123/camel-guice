package iblutha.demo.eip.littleshop.backend.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iblutha.demo.eip.littleshop.api.events.UserAdded;

import javax.validation.constraints.NotNull;

/**
 * Created by tibo on 29/11/2015.
 */
@JsonDeserialize(builder = BackendUserAdded.Builder.class)
public class BackendUserAdded implements UserAdded {
    @NotNull
    private String response;

    private BackendUserAdded(Builder builder) {
        this.response = builder.response;
    }

    public static Builder newBuilder() { return new Builder();}

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String response;

        public Builder response(String response){
            this.response = response;
            return this;
        }
        public BackendUserAdded build() {
            return new BackendUserAdded(this);
        }
    }

    @Override
    public String getResponse() {
        return response;
    }

    public String toString() {
        return new StringBuilder("BackendUserAdded [")
                .append("response : ").append(response).append("]")
                .toString();
    }
}
