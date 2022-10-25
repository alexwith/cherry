package net.cherry.entity;

public class EntityController<T> {
    private final Class<T> originClass;
    private final T object;
    private final EntityStorage storage;

    private EntityController<?> rootController;

    public EntityController(Class<T> originClass, T object) {
        this.originClass = originClass;
        this.object = object;
        this.storage = new EntityStorage();
    }

    public Class<T> getOriginClass() {
        return this.originClass;
    }

    public T getObject() {
        return this.object;
    }

    public EntityStorage getStorage() {
        return this.storage;
    }

    public EntityController<?> getRootController() {
        return this.rootController;
    }

    public void setRootController(EntityController<?> rootController) {
        this.rootController = rootController;
    }

    public String getDetailedName() {
        return this.originClass + "$Proxied@" + this.object.hashCode();
    }
}
