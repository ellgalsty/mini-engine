package com.bootcamp.demo.pages.containers;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.TacticalGameData;
import com.bootcamp.demo.data.save.TacticalSaveData;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.managers.API;

public class TacticalContainer extends BorderedTable {
    private final Image icon;

    public TacticalContainer () {
        setTouchable(Touchable.disabled);

        icon = new Image();
        icon.setScaling(Scaling.fit);
        add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
    }

    public void setData (@Null TacticalSaveData tacticalSaveData) {
        if (tacticalSaveData == null) {
            setEmpty();
            return;
        }
        final TacticalGameData tacticalGameData = API.get(GameData.class).getTacticalsGameData().getTacticals().get(tacticalSaveData.getName());
        icon.setDrawable(tacticalGameData.getIcon());
        setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(tacticalSaveData.getRarity().getBackgroundColor())));
        setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get(tacticalSaveData.getRarity().getBorderColor())));
    }

    @Override
    public void setEmpty () {
        super.setEmpty();
        icon.setDrawable(null);
    }
}
