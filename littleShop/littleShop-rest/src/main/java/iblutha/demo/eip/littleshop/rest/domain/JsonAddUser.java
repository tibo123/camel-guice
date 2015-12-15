package iblutha.demo.eip.littleshop.rest.domain;

import iblutha.demo.eip.littleshop.api.commands.AddUser;

import javax.validation.constraints.NotNull;

/**
 * Created by tibo on 29/11/2015.
 */
//@JsonDeserialize(builder = JsonAddUser.Builder.class)
public class JsonAddUser implements AddUser {
    @NotNull
    private String email;
    private String firstname;
    private String lastname;

    private JsonAddUser(Builder builder) {
        this.email = builder.email;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
    }

//    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String email;
        private String firstname;
        private String lastname;

        public Builder email(String email){
            this.email = email;
            return this;
        }
        public Builder firstname(String firstname){
            this.firstname = firstname;
            return this;
        }
        public Builder lastname(String lastname){
            this.lastname = lastname;
            return this;
        }
        public JsonAddUser build() {
            return new JsonAddUser(this);
        }
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    public String toString() {
        return new StringBuilder("JsonAddUser [")
                .append("firstname : ").append(firstname).append(",")
                .append("lastname : ").append(lastname).append(",")
                .append("email : ").append(email).append("]")
                .toString();
    }
}
