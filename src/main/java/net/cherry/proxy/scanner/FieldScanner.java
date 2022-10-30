package net.cherry.proxy.scanner;

import java.lang.reflect.Field;
import net.cherry.proxy.entity.ProxiedClass;
import net.cherry.proxy.entity.ProxyField;

public class FieldScanner implements Scanner {

    @Override
    public void scan(ProxiedClass<?> proxiedClass) {
        final Class<?> clazz = proxiedClass.getOriginClass();
        for (final Field field : clazz.getDeclaredFields()) {
            final ProxyField proxyField = new ProxyField(field);
            proxiedClass.addField(proxyField);
        }
    }
}
