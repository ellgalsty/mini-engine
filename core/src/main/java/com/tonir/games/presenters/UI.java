package com.tonir.games.presenters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UI {

    private final Stage stage;

    public UI (Viewport viewport) {
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
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
