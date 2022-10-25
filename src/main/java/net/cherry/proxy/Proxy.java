package net.cherry.proxy;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntityControllerManager;
import net.cherry.proxy.entity.ProxiedClass;
import net.cherry.proxy.interceptor.Interceptor;
import net.cherry.proxy.interceptor.ToStringInterceptor;

public class Proxy {
    private static final Map<Class<?>, ProxiedClass<?>> PROXIED = new ConcurrentHashMap<>();
    private static final Set<Interceptor> INTERCEPTORS = Set.of(
        new ToStringInterceptor()
    );

    @SuppressWarnings("unchecked")
    public static <T> ProxiedClass<T> proxyClass(Class<T> originClass) {
        if (PROXIED.containsKey(originClass)) {
            return (ProxiedClass<T>) PROXIED.get(originClass);
        }

        Builder<?> builder = new ByteBuddy().subclass(originClass);

        for (final Interceptor interceptor : INTERCEPTORS) {
            builder = interceptor.create(builder);
        }

        final Class<T> clazz = (Class<T>) builder.make().load(originClass.getClassLoader()).getLoaded();

        return new ProxiedClass<>(clazz, originClass);
    }

    public static <T> T createProxiedEntity(ProxiedClass<T> proxiedClass) {
        final Constructor<T> constructor = proxiedClass.getConstructor();

        try {
            final T entity = constructor.newInstance();
            final Class<T> originClass = proxiedClass.getOriginClass();
            final EntityController<T> controller = new EntityController<>(originClass, entity);

            EntityControllerManager.registerController(controller);

            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
