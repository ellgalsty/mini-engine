package com.tonir.games.managers;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.tonir.games.managers.event.EventModule;
import com.tonir.games.utils.Resources;

public class API implements Disposable {

    private static API api;

    private final ObjectMap<Class<?>, Disposable> customMap = new ObjectMap<>();

    public static API Instance () {
        if (api == null) {
            api = new API();
        }
        return api;
    }

    public API () {
        initMinimal();
    }

    public static <U> U get (Class<U> clazz) {
        return clazz.cast(Instance().customMap.get(clazz));
    }

    public void initMinimal () {
        register(new EventModule());
        register(Resources.class);
    }

    public <T extends Disposable> void register (Class<T> key, T object) {
        if (customMap.containsKey(key)) return;
        customMap.put(key, object);
    }

    public <T extends Disposable> void register (Class<T> clazz) {
        if (customMap.containsKey(clazz)) return;
        try {
            T instance = ClassReflection.newInstance(clazz);
            customMap.put(clazz, instance);
        } catch (ReflectionException e) {
            throw new RuntimeException("Failed to instantiate class: " + clazz.getName(), e);
        }
    }

    public <T extends Disposable> void register (T object) {
        register((Class<T>) object.getClass(), object);
    }

    @Override
    public void dispose () {
        for (Disposable disposable : customMap.values()) {
            disposable.dispose();
        }
        customMap.clear();
    }
}
