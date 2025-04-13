package com.bootcamp.demo.presenters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bootcamp.demo.events.core.EventListener;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.MissionsPage;
import com.bootcamp.demo.pages.TestPage;
import com.bootcamp.demo.pages.homeworks.HomeworkOne;
import com.bootcamp.demo.pages.core.APage;
import com.bootcamp.demo.pages.core.PageManager;
import com.bootcamp.demo.pages.homeworks.HomeworkTwo;
import lombok.Getter;
import lombok.Setter;

public class GameUI extends ScreenAdapter implements Disposable, EventListener {

    @Getter
    private final Stage stage;
    @Getter
    private final Table rootUI;
    @Getter
    private final Cell<APage> mainPageCell;

    @Getter @Setter
    private boolean buttonPressed;

    public GameUI (Viewport viewport) {
        API.Instance().register(GameUI.class, this);
        API.get(EventModule.class).registerListener(this);

        rootUI = new Table();
        rootUI.setFillParent(true);

        // init stage
        stage = new Stage(viewport);
        stage.addActor(rootUI);

        // construct
        mainPageCell = rootUI.add().grow();

        API.get(PageManager.class).show(TestPage.class);
    }

    @Override
    public void render (float delta) {
        if (Gdx.app.getInput().isKeyJustPressed(Input.Keys.L)) {
            API.get(PageManager.class).show(HomeworkOne.class);
        }
        if (Gdx.app.getInput().isKeyJustPressed(Input.Keys.K)) {
            API.get(PageManager.class).show(HomeworkTwo.class);
        }
        if(Gdx.app.getInput().isKeyJustPressed(Input.Keys.M)) {
            API.get(PageManager.class).show(MissionsPage.class);
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
