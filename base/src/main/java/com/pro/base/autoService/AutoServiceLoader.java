package com.pro.base.autoService;

import java.util.ServiceLoader;

public final class AutoServiceLoader {
    private AutoServiceLoader() {
    }

    public static <S> S load(Class<S> service) {
        try {
            return ServiceLoader.load(service).iterator().next();
        } catch (Exception e) {
            return null;
        }
    }
}
