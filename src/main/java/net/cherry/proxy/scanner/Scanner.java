package net.cherry.proxy.scanner;

import net.cherry.proxy.entity.ProxiedClass;

public interface Scanner {

    void scan(ProxiedClass<?> proxiedClass);
}
