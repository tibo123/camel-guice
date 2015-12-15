package iblutha.demo.eip.littleshop.backend.domain;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import iblutha.demo.eip.littleshop.api.commands.AddUser;
import iblutha.demo.eip.littleshop.api.events.AddUserFailed;

import javax.validation.constraints.NotNull;

/**
 * Created by tibo on 05/12/2015.
 */
public class BackendAddUserFailed implements AddUserFailed {
    @NotNull
    private String email;
    private String firstname;
    private String lastname;
    private String reason;

    private BackendAddUserFailed(Builder builder) {
        this.email = builder.email;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.reason = builder.reason;
    }

    public static Builder copyBuilder(AddUser addUser) {
        return new Builder()
                .email(addUser.getEmail())
                .firstname(addUser.getFirstname())
                .lastname(addUser.getLastname());
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String email;
        private String firstname;
        private String lastname;
        private String reason;

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
        public Builder reason(String reason){
            this.reason = reason;
            return this;
        }
        public BackendAddUserFailed build() {
            return new BackendAddUserFailed(this);
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

    @Override
    public String getReason() {
        return reason;
    }

    public String toString() {
        return new StringBuilder("BackendAddUser [")
                .append("firstname : ").append(firstname).append(",")
                .append("lastname : ").append(lastname).append(",")
                .append("email : ").append(email).append(",")
                .append("reason : ").append(reason).append("]")
                .toString();
    }
}
