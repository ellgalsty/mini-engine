package com.bootcamp.demo.pages.containers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.MilitaryGearSlot;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.MilitaryGearGameData;
import com.bootcamp.demo.data.save.MilitaryGearSaveData;
import com.bootcamp.demo.dialogs.GearDialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.MissionsPage;

public class GearContainer extends BorderedTable {
    private final Image icon;
    private final MilitaryGearSlot slot;
    private Label levelLabel;
    private Label rankLabel;
    private final MissionsPage.StarsContainer starsContainer;


    public GearContainer (MilitaryGearSlot slot) {
        this.slot = slot;

        starsContainer = new MissionsPage.StarsContainer();
        final Table overlay = constructOverlay();
        icon = new Image();
        icon.setScaling(Scaling.fit);

        add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        addActor(overlay);
    }

    public void setData (MilitaryGearSaveData gearSaveData) {
        if (gearSaveData == null) {
            setEmpty();
            return;
        }
        final MilitaryGearGameData militaryGameData = API.get(GameData.class).getMilitaryGearsGameData().getGears().get(gearSaveData.getName());
        icon.setDrawable(militaryGameData.getIcon());
        levelLabel.setText("Lv. " + gearSaveData.getLevel());
        rankLabel.setText(gearSaveData.getRank());
        starsContainer.setData(gearSaveData.getStarCount());
        setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(gearSaveData.getRarity().getBackgroundColor())));
        setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get(gearSaveData.getRarity().getBorderColor())));
    }

    private Table constructOverlay () {
        levelLabel = Labels.make(GameFont.BOLD_20, ColorLibrary.get("fef4ee"));
        rankLabel = Labels.make(GameFont.BOLD_20, ColorLibrary.get("fef4ee"));


        final Table segment = new Table();
        segment.pad(10).defaults().expand();
        segment.add(starsContainer).top().left();
        segment.row();
        segment.add(levelLabel).bottom().left();
        segment.add(rankLabel).bottom().right();
        segment.setFillParent(true);

        return segment;
    }
}
