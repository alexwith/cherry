package net.cherry.entity;

import java.util.HashMap;
import java.util.Map;

public class EntityControllerManager {
    private static final Map<Object, EntityController<?>> CONTROLLERS = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> EntityController<T> getController(Object entity) {
        final EntityController<T> controller = (EntityController<T>) CONTROLLERS.get(entity);
        if (controller == null) {
            throw new IllegalStateException("Could not find a controller associated to the provided entity");
        }

        return controller;
    }

    public static boolean hasController(Object entity) {
        return CONTROLLERS.containsKey(entity);
    }

    public static void registerController(EntityController<?> controller) {
        CONTROLLERS.put(controller.getObject(), controller);
    }
}
