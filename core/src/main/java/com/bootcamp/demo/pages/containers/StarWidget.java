package com.bootcamp.demo.pages.containers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.Resources;

public class StarWidget extends Table {
    private Image star;

    public StarWidget () {
        star = new Image();
        add(star).size(30);
    }

    protected void setData () {
        star.setDrawable(Resources.getDrawable("ui/star"));
    }
}
