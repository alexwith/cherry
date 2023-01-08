package net.cherry.entity;

import net.cherry.proxy.entity.ProxiedClass;

public class EntityController<T extends Entity<T>> {
    private final T object;
    private final Class<T> originClass;
    private final ProxiedClass<T> proxiedClass;
    private final EntityStorage storage;
    private final EntitySettings<T> settings;

    private EntityController<?> rootController;

    public EntityController(T object, Class<T> originClass, ProxiedClass<T> proxiedClass) {
        this.object = object;
        this.originClass = originClass;
        this.proxiedClass = proxiedClass;
        this.storage = new EntityStorage();
        this.settings = object.settings().apply(new EntitySettings<>());
    }

    public T getObject() {
        return this.object;
    }

    public Class<T> getOriginClass() {
        return this.originClass;
    }

    public ProxiedClass<T> getProxiedClass() {
        return this.proxiedClass;
    }

    public EntityStorage getStorage() {
        return this.storage;
    }

    public EntitySettings<T> getSettings() {
        return this.settings;
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
