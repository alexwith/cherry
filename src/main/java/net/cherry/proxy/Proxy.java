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

public class Proxy {

    private static final Map<Class<?>, Class<?>> PROXIED_CLASSES = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Constructor<?>> PROXIED_CONSTRUCTORS = new ConcurrentHashMap<>();
    private static final Set<Interceptor> INTERCEPTORS = Set.of(
        new ToStringInterceptor()
    );

    @SuppressWarnings("unchecked")
    public static <T> Class<T> proxyClass(Class<T> clazz) {
        if (PROXIED_CLASSES.containsKey(clazz)) {
            return (Class<T>) PROXIED_CLASSES.get(clazz);
        }

        Builder<?> builder = new ByteBuddy().subclass(clazz);

        for (final Interceptor interceptor : INTERCEPTORS) {
            builder = interceptor.create(builder);
        }

        final Class<T> proxiedClass = (Class<T>) builder.make().load(clazz.getClassLoader()).getLoaded();
        final Constructor<T> constructor = (Constructor<T>) proxiedClass.getDeclaredConstructors()[0];

        PROXIED_CLASSES.put(clazz, proxiedClass);
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
            final EntityController<T> controller = new EntityController<>(proxiedClass, entity);

            EntityControllerManager.registerController(controller);

            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
