package iblutha.demo.utils;

import java.util.List;

/**
 * Created by tibo on 14/11/2015.
 */
public interface Mapper<From, To> {

    To map (From from);

    List<To> mapAll (List<From> from);
}
