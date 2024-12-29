package com.tonir.demo.presenters.utils.pages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tonir.demo.events.PageClosedEvent;
import com.tonir.demo.events.PageOpenedEvent;
import com.tonir.demo.utils.Resources;
import lombok.Getter;
import lombok.Setter;

public abstract class APage extends Table {

    protected Table content;
    @Getter
    private boolean shown;
    @Setter
    private Runnable onShowComplete;
    @Setter
    private Runnable onHideComplete;

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

    public void show () {
        shown = true;
        PageOpenedEvent.fire(this.getClass());
        if (onShowComplete != null) {
            onShowComplete.run();
            onShowComplete = null;
        }
    }

    public void hide () {
        shown = false;
        PageClosedEvent.fire(this.getClass());
        if (onHideComplete != null) {
            onHideComplete.run();
            onHideComplete = null;
        }
    }
}
