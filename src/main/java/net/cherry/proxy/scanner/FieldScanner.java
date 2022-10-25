package net.cherry.proxy.scanner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.cherry.proxy.entity.ProxiedClass;
import net.cherry.proxy.entity.ProxyField;

public class FieldScanner implements Scanner {

    @Override
    public void scan(ProxiedClass<?> proxiedClass) {
        final Class<?> clazz = proxiedClass.getOriginClass();
        for (final Field field : clazz.getDeclaredFields()) {
            final Method matchedMethod = this.getMatchingMethod(clazz, field);
            if (matchedMethod == null) {
                continue;
            }

            final ProxyField proxyField = new ProxyField(field, matchedMethod);
            proxiedClass.addField(proxyField);
        }
    }

    private Method getMatchingMethod(Class<?> clazz, Field field) {
        final String name = field.getName();
        final String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
        try {
            return clazz.getDeclaredMethod("get".concat(capitalizedName));
        } catch (Throwable ignore) {
            try {
                return clazz.getDeclaredMethod("is".concat(capitalizedName));
            } catch (Throwable ignore2) {
                return null;
            }
        }
    }
}
