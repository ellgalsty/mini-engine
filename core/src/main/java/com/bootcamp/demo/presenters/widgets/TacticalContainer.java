package com.bootcamp.demo.presenters.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.TacticalGameData;
import com.bootcamp.demo.data.save.TacticalSaveData;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.containers.StarsContainer;
import lombok.Getter;

public class TacticalContainer extends BorderedTable {
    protected final Image icon;
    protected final Label levelLabel;
    protected final StarsContainer starsContainer;
    @Getter
    protected TacticalSaveData tacticalSaveData;

    public TacticalContainer () {
        starsContainer = new StarsContainer();
        icon = new Image();
        icon.setScaling(Scaling.fit);
        levelLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("ebe6d9"));

        setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("a8f782")));

        final Table overlay = constructOverlay();
        add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        addActor(overlay);
    }


    public void setData (@Null TacticalSaveData tacticalSaveData) {
        this.tacticalSaveData = tacticalSaveData;
        if (tacticalSaveData == null) {
            setEmpty();
            return;
        }
        final TacticalGameData gameData = API.get(GameData.class).getTacticalsGameData().getTacticalsMap().get(tacticalSaveData.getName());

        levelLabel.setText("lv" + tacticalSaveData.getLevel());
        starsContainer.setData(tacticalSaveData.getStarCount());
        icon.setDrawable(gameData.getIcon());

        setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(tacticalSaveData.getRarity().getBackgroundColor())));
    }

    @Override
    public void setEmpty () {
        super.setEmpty();
        icon.setDrawable(null);
        levelLabel.setText("");
        setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bcbcbc")));
        setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("7d7d7d")));
    }


    private Table constructOverlay () {
        Table segment = new Table();
        segment.pad(10).defaults().expand();
        segment.add(levelLabel).top().left();
        segment.add(starsContainer).top().right();
        segment.setFillParent(true);
        return segment;
    }
}
