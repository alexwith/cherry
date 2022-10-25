package net.cherry.proxy.entity;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxiedClass<T> {
    private final Class<T> clazz;
    private final Class<T> originClass;
    private final Constructor<T> constructor;
    private final Object[] emptyConstructorArgs;
    private final Map<Method, ProxyField> fields = new HashMap<>();

    @SuppressWarnings("unchecked")
    public ProxiedClass(Class<T> clazz, Class<T> originClass) {
        this.clazz = clazz;
        this.originClass = originClass;
        this.constructor = (Constructor<T>) clazz.getDeclaredConstructors()[0];
        this.emptyConstructorArgs = this.createEmptyConstructorArgs();
    }

    public Class<T> getClazz() {
        return this.clazz;
    }

    public Class<T> getOriginClass() {
        return this.originClass;
    }

    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    public Object[] getEmptyConstructorArgs() {
        return this.emptyConstructorArgs;
    }

    public void addField(ProxyField field) {
        this.fields.put(field.getMethod(), field);
    }

    public ProxyField getField(Method method) {
        return this.fields.get(method);
    }

    private Object[] createEmptyConstructorArgs() {
        final Parameter[] parameters = this.constructor.getParameters();
        final List<Object> args = new ArrayList<>(parameters.length);
        for (final Parameter parameter : parameters) {
            args.add(this.getDefaultValue(parameter.getType()));
        }

        return args.toArray();
    }

    @SuppressWarnings("unchecked")
    private <V> V getDefaultValue(Class<V> clazz) {
        return (V) Array.get(Array.newInstance(clazz, 1), 0);
    }
}
