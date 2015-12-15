package iblutha.demo.eip.littleshop.api.domain;

import java.io.Serializable;

/**
 * Created by tibo on 29/11/2015.
 */
public interface User extends Serializable{

    String getEmail();
    String getFirstname();
    String getLastname();
}
