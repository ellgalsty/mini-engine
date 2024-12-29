package com.tonir.demo.managers;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.tonir.demo.presenters.UI;
import com.tonir.demo.presenters.utils.pages.APage;

public class PageManager implements Disposable {

    private final ObjectMap<Class<? extends APage>, APage> pageCache = new ObjectMap<>();

    public <T extends APage> T getPage (Class<T> clazz) {
        if (!pageCache.containsKey(clazz)) {
            try {
                final APage page = ClassReflection.newInstance(clazz);
                pageCache.put(clazz, page);
                return (T) page;
            } catch (ReflectionException e) {
                throw new RuntimeException(e);
            }
        }
        return (T) pageCache.get(clazz);
    }

    public void showPage (Class<? extends APage> clazz) {
        final Cell<APage> pageCell = API.get(UI.class).getPageCell();
        // get the page to show
        final APage page = getPage(clazz);
        // close currently opened page before showing the next page
        hidePage();
        // visually show the page
        pageCell.setActor(page);
        page.show();
    }

    public void hidePage () {
        final Cell<APage> pageCell = API.get(UI.class).getPageCell();
        final APage currentPage = pageCell.getActor();
        if (currentPage == null) return;
        pageCell.setActor(null);
        currentPage.hide();
    }

    @Override
    public void dispose () {
        pageCache.clear();
    }
}
