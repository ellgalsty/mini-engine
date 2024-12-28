package com.tonir.games.presenters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UI {
    private final Stage stage;
    private final Table rootUI;

    public UI (Viewport viewport) {
        // init stage
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);


        rootUI = new Table();
        rootUI.setFillParent(true);
        stage.addActor(rootUI);

        // init components
        final BottomPanel bottomPanel = new BottomPanel();
        rootUI.add(bottomPanel).growX().height(200);
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void dispose () {
        stage.dispose();
    }

    public void render () {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
