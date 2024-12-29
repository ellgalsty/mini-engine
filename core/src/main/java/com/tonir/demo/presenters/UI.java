package com.tonir.demo.presenters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tonir.demo.managers.API;
import com.tonir.demo.presenters.utils.pages.APage;
import lombok.Getter;

public class UI implements Disposable {
    private final Stage stage;
    private final Table rootUI;
    @Getter
    private final Cell<APage> pageCell;

    public UI (Viewport viewport) {
        API.Instance().register(UI.class, this);

        // init stage
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        rootUI = new Table();
        rootUI.setFillParent(true);
        stage.addActor(rootUI);

        // init components
        final BottomPanel bottomPanel = new BottomPanel();

        // construct
        pageCell = rootUI.add().grow();
        rootUI.row();
        rootUI.add(bottomPanel).growX().height(300);
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }

    public void render () {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
