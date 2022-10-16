package net.cherry.proxy;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityControllerManager;
import net.cherry.proxy.interceptor.Interceptor;
import net.cherry.proxy.interceptor.ToStringInterceptor;
import net.cherry.util.BiMap;

public class Proxy {

    private static final BiMap<Class<?>, Class<?>> PROXIED_CLASSES = new BiMap<>();
    private static final Map<Class<?>, Constructor<?>> PROXIED_CONSTRUCTORS = new ConcurrentHashMap<>();
    private static final Set<Interceptor> INTERCEPTORS = Set.of(
        new ToStringInterceptor()
    );

    @SuppressWarnings("unchecked")
    public static <T> Class<T> proxyClass(Class<T> originClass) {
        if (PROXIED_CLASSES.containsKey(originClass)) {
            return (Class<T>) PROXIED_CLASSES.get(originClass);
        }

        Builder<?> builder = new ByteBuddy().subclass(originClass);

        for (final Interceptor interceptor : INTERCEPTORS) {
            builder = interceptor.create(builder);
        }

        final Class<T> proxiedClass = (Class<T>) builder.make().load(originClass.getClassLoader()).getLoaded();
        final Constructor<T> constructor = (Constructor<T>) proxiedClass.getDeclaredConstructors()[0];

        PROXIED_CLASSES.put(originClass, proxiedClass);
        PROXIED_CONSTRUCTORS.put(proxiedClass, constructor);

        return proxiedClass;
    }

    @SuppressWarnings("unchecked")
    public static <T> T createProxiedEntity(Class<T> proxiedClass) {
        if (!PROXIED_CONSTRUCTORS.containsKey(proxiedClass)) {
            throw new IllegalStateException("Tried creating an instance of a non-proxied class.");
        }

        final Constructor<T> constructor = (Constructor<T>) PROXIED_CONSTRUCTORS.get(proxiedClass);

        try {
            final T entity = constructor.newInstance();
            final Class<T> originClass = (Class<T>) PROXIED_CLASSES.inverted().get(proxiedClass);
            final EntityController<T> controller = new EntityController<>(originClass, entity);

            EntityControllerManager.registerController(controller);

            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
