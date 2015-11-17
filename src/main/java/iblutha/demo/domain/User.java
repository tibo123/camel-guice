package iblutha.demo.domain;

/**
 * Created by talend on 13.11.15.
 */
public class User {
    private int id;
    private String firstname;
    private String lastname;

    private User(Builder builder) {
        this.id = builder.id;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder copyBuilder(User user) {
        return new Builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname());
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String toString() {
        return new StringBuilder("User [")
                .append("id").append(id).append(",")
                .append("firstname").append(firstname).append(",")
                .append("lastname").append(lastname).append("]")
                .toString();
    }

    public static class Builder {
        private int id;
        private String firstname;
        private String lastname;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
