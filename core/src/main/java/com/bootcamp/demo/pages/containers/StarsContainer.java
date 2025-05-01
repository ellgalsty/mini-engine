package com.bootcamp.demo.pages.containers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Pools;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;

public class StarsContainer extends WidgetsContainer<StarsContainer.StarWidget> {
    public StarsContainer () {
        super(Integer.MAX_VALUE);
        reserveCells(false);
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

    public static class StarWidget extends Table {
        private final Image star;

        public StarWidget () {
            star = new Image();
            add(star).size(30);
        }

        protected void setData () {
            star.setDrawable(Resources.getDrawable("ui/star"));
        }
    }
}
