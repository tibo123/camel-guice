package iblutha.demo.routes;

import iblutha.demo.utils.Mapper;
import org.apache.camel.builder.RouteBuilder;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by tibo on 14/11/2015.
 */
public abstract class ExtendedRouteBuilder extends RouteBuilder {

    /**
     *
     * @param mapper
     * @return
     */
    public String mapper(final Class<? extends Mapper<?, ?>> mapper) {
        return MessageFormat.format("bean:{0}?method=map", getName(mapper));
    }

    /**
     *
     * @param type
     * @return
     */
    public String validator(final Class<?> type) {
        return MessageFormat.format("bean-validator:{0}", getDefaultName(type));
    }

    private String getName(Class<?> beanType) {
        final Map<String, ?> names = getContext().getRegistry().findByTypeWithName(beanType);
        if (names == null || names.size() != 1) {
            return getDefaultName(beanType);
        }

        return names.keySet().iterator().next();
    }

    private String getDefaultName(Class<?> type) {
        return type.getName();
    }
}
