package com.bootcamp.demo.pages.containers;

import com.badlogic.gdx.utils.Pools;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;

public class StarsContainer extends WidgetsContainer<StarWidget> {
    public StarsContainer () {
        super(4);
        defaults().space(5).size(30);
    }

    public void setData (int starCount) {
        freeChildren();
        for (int i = 0; i < starCount; i++) {
            final StarWidget star = Pools.obtain(StarWidget.class);
            add(star);
            star.setData();
        }
    }
}
