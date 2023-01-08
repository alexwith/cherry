package net.cherry.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeHolder {
    private final Class<?> baseType;
    private final Type[] typeArgs;

    public TypeHolder(Field field) {
        this.baseType = field.getType();
        this.typeArgs = this.extractTypeArgs(field);
    }

    public Class<?> getType() {
        return this.baseType;
    }

    public Type[] getTypeArgs() {
        return this.typeArgs;
    }

    private Type[] extractTypeArgs(Field field) {
        if (field.getGenericType() instanceof final ParameterizedType parameterizedType) {
            return parameterizedType.getActualTypeArguments();
        }

        return new Type[0];
    }
}
