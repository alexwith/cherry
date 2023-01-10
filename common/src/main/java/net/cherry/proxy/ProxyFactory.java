package net.cherry.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.cherry.entity.Entity;
import net.cherry.entity.EntityController;
import net.cherry.entity.EntitySettings;
import net.cherry.entity.EntityStorage;
import net.cherry.proxy.entity.ProxiedClass;
import net.cherry.proxy.entity.ProxyField;
import net.cherry.proxy.entity.ProxyMetadata;
import net.cherry.proxy.interceptor.GetterInterceptor;
import net.cherry.proxy.interceptor.Interceptor;
import net.cherry.proxy.interceptor.SetterInterceptor;
import net.cherry.proxy.interceptor.ToStringInterceptor;
import net.cherry.proxy.scanner.FieldScanner;
import net.cherry.proxy.scanner.Scanner;
import net.cherry.util.SneakyThrows;

public class ProxyFactory {
    private static final Map<Class<?>, ProxiedClass<?>> PROXIED_CLASSES = new ConcurrentHashMap<>();
    private static final Set<Interceptor> INTERCEPTORS = Set.of(
        new ToStringInterceptor(),
        new GetterInterceptor(),
        new SetterInterceptor()
    );
    private static final Set<Scanner> SCANNERS = Set.of(
        new FieldScanner()
    );

    @SuppressWarnings("unchecked")
    public static <T> ProxiedClass<T> createProxiedClass(Class<T> originClass) {
        if (PROXIED_CLASSES.containsKey(originClass)) {
            return (ProxiedClass<T>) PROXIED_CLASSES.get(originClass);
        }

        Builder<?> builder = new ByteBuddy().subclass(originClass);

        builder = builder.defineProperty("controller", EntityController.class);

        for (final Interceptor interceptor : INTERCEPTORS) {
            builder = interceptor.create(builder);
        }

        final Class<T> clazz = (Class<T>) builder.make().load(originClass.getClassLoader()).getLoaded();
        final ProxiedClass<T> proxiedClass = new ProxiedClass<>(clazz, originClass);

        for (final Scanner scanner : SCANNERS) {
            scanner.scan(proxiedClass);
        }

        PROXIED_CLASSES.put(originClass, proxiedClass);

        return proxiedClass;
    }

    public static <T extends Entity<T>> T createProxiedEntity(ProxiedClass<T> proxiedClass, Object id) {
        final Constructor<T> constructor = proxiedClass.getConstructor();
        final ProxyMetadata metadata = proxiedClass.getMetadata();

        return SneakyThrows.supply(() -> {
            final T entity = constructor.newInstance(proxiedClass.getEmptyConstructorArgs());
            final Class<T> originClass = proxiedClass.getOriginClass();
            final EntityController<T> controller = new EntityController<>(entity, originClass, proxiedClass);
            final EntityStorage storage = controller.getStorage();
            final EntitySettings<T> settings = controller.getSettings();

            final T defaultEntity = settings.getDefaultEntity();
            if (defaultEntity != null) {
                for (final ProxyField proxyField : proxiedClass.getFields().values()) {
                    final Field field = proxyField.getField();
                    field.setAccessible(true);

                    final Object value = field.get(defaultEntity);
                    if (value == null) {
                        continue;
                    }

                    storage.set(proxyField.getPath(), value);
                }
            }

            if (id != null) {
                storage.set(metadata.getIdField(), id);
            }

            final Field controllerField = proxiedClass.getClazz().getDeclaredField("controller");
            controllerField.setAccessible(true);
            controllerField.set(entity, controller);

            return entity;
        });
    }
}
