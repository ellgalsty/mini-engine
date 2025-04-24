package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.data.MilitaryGearSlot;
import com.bootcamp.demo.data.save.MilitaryGearSaveData;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.pages.containers.GearContainer;

public class GearDialog extends ADialog {
    private static final float WIDGET_SIZE = 300f;
    private MilitaryGearSlot slot;
    private GearContainer container;
    private Label rarityValueLabel;
    private Label typeValueLabel;
    private Label powerValueLabel;

    @Override
    protected void constructContent (Table content) {
        setTitle("Gear Info", Color.valueOf("494846"));

        final Table mainDialog = constructMainDialog();
        content.pad(30);
        content.add(mainDialog).width(1000);
    }

    private Table constructMainDialog () {
        container = new GearContainer(slot);
        final Table gearInfoSegment = constructGearInfoSegment();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bfbab8")));
        segment.pad(25).defaults().space(25);
        segment.add(container).size(WIDGET_SIZE);
        segment.add(gearInfoSegment).grow();
        return segment;
    }

    private Table constructGearInfoSegment () {
        final Label powerLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("403837"));
        powerLabel.setText("Power:");
        final Label rarityLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("403837"));
        rarityLabel.setText("Rarity:");
        final Label typeLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("403837"));
        typeLabel.setText("Type:");

        powerValueLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("fdf3eb"));
        rarityValueLabel = Labels.make(GameFont.BOLD_20);
        typeValueLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("fdf3eb"));

        final Table segment = new Table();
        segment.defaults().expand();
        segment.add(powerLabel).left();
        segment.add(powerValueLabel).right();
        segment.row();
        segment.add(rarityLabel).left();
        segment.add(rarityValueLabel).right();
        segment.row();
        segment.add(typeLabel).left();
        segment.add(typeValueLabel).right();
        return segment;
    }

    public void setData (MilitaryGearSaveData gearSaveData, MilitaryGearSlot slot) {
        this.slot = slot;
        container.setData(gearSaveData);
        powerValueLabel.setText(String.valueOf(gearSaveData.getPower()));
        rarityValueLabel.setText(gearSaveData.getRarity().getStringValue());
        rarityValueLabel.setColor(Color.valueOf(gearSaveData.getRarity().getBackgroundColor()));
        typeValueLabel.setText(slot.getStringValue());
    }

}
