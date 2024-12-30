package com.tonir.demo.utils.presenters.widgets.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tonir.demo.utils.Resources;
import lombok.Getter;

public abstract class APage extends Table {

    protected Table content;
    @Getter
    private boolean shown;

    public APage () {
        setBackground(Resources.getDrawable("basics/white-pixel", Color.valueOf("#69605Bff")));
        setTouchable(Touchable.enabled);
        addListener(new ClickListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void clicked (InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });

        content = new Table();
        add(content).grow();
        constructContent(content);
    }

    protected abstract void constructContent (Table content);

    public void show (Runnable onComplete) {
        shown = true;
        if (onComplete != null) {
            onComplete.run();
        }
    }

    public void hide (Runnable onComplete) {
        shown = false;
        if (onComplete != null) {
            onComplete.run();
        }
    }
}
