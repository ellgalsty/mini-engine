package com.bootcamp.demo.pages.containers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.MilitaryGearSlot;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.MilitaryGearGameData;
import com.bootcamp.demo.data.save.MilitaryGearSaveData;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import lombok.Getter;

public class GearContainer extends BorderedTable {
    private final Image icon;
    private MilitaryGearSlot slot;
    private Label levelLabel;
    private Label rankLabel;
    private final StarsContainer starsContainer;

    @Getter
    private MilitaryGearSaveData data;

    public GearContainer() {
        starsContainer = new StarsContainer();
        final Table overlay = constructOverlay();
        icon = new Image();
        icon.setScaling(Scaling.fit);

        add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        addActor(overlay);
    }

    public GearContainer (MilitaryGearSlot slot) {
        this.slot = slot;

        starsContainer = new StarsContainer();
        final Table overlay = constructOverlay();
        icon = new Image();
        icon.setScaling(Scaling.fit);

        add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        addActor(overlay);
    }

    public void setData (MilitaryGearSaveData data) {
        this.data = data;

        if (data == null) {
            setEmpty();
            return;
        }
        final MilitaryGearGameData militaryGameData = API.get(GameData.class).getMilitaryGearsGameData().getGears().get(data.getName());
        icon.setDrawable(militaryGameData.getIcon());
        levelLabel.setText("Lv. " + data.getLevel());
        rankLabel.setText(data.getRank());
        starsContainer.setData(data.getStarCount());
        setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(data.getRarity().getBackgroundColor())));
        setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get(data.getRarity().getBorderColor())));
    }

    @Override
    public void setEmpty () {
        super.setEmpty();
        setBackground((Drawable) null);
        icon.setDrawable(null);
        levelLabel.setText("");
        rankLabel.setText("");
        starsContainer.setData(0);
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
