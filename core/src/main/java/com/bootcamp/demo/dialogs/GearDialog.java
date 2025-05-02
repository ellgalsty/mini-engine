package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;
import com.bootcamp.demo.data.MilitaryGearSlot;
import com.bootcamp.demo.data.MissionsManager;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.MilitaryGearsGameData;
import com.bootcamp.demo.data.save.MilitaryGearSaveData;
import com.bootcamp.demo.dialogs.buttons.TextOffsetButton;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.containers.GearContainer;
import com.bootcamp.demo.pages.containers.StatsContainer;

public class GearDialog extends ADialog {
    private static final float WIDGET_SIZE = 300f;
    private static GearContainer currentSelectedContainer;
    private static Label rarityValueLabel;
    private static Label typeValueLabel;
    private static Label powerValueLabel;
    private static Label gearTitle;
    private static StatsContainer gearStats;
    private MilitaryGearSlot slot;
    private TextOffsetButton equipButton;

    @Override
    protected void constructContent (Table content) {
        setTitle("Gear Info", Color.valueOf("494846"));

        final Table mainDialog = constructMainDialog();
        content.pad(30);
        content.add(mainDialog).width(1000);
    }

    private Table constructMainDialog () {
        final Table gearInfoWrapper = constructGearInfoWrapper();
        final Table statsWrapper = constructStatsWrapper();
        equipButton = new TextOffsetButton(OffsetButton.Style.GREEN_35);
        equipButton.setOnClick(() -> {
            MissionsManager.equipGear(currentSelectedContainer.getData());
        });

        final Table segment = new Table();
        segment.pad(25).defaults().space(25).grow();
        segment.add(gearInfoWrapper);
        segment.row();
        segment.add(statsWrapper);
        return segment;
    }

    private Table constructStatsWrapper () {
        final Label statsTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("2c1a12"));
        statsTitle.setText("Gear Stats");
        gearStats = new StatsContainer();
        gearStats.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("af9e90")));

        final Table segment = new Table();
        segment.defaults().space(25);
        segment.add(statsTitle);
        segment.row();
        segment.add(gearStats).grow();
        return segment;
    }

    private Table constructGearInfoWrapper () {
        gearTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("38302e"));
        currentSelectedContainer = new GearContainer(slot);
        final Table gearInfoSegment = constructGearInfoSegment();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bfbab8")));
        segment.pad(25).defaults().space(25);
        segment.add(gearTitle);
        segment.row();
        segment.add(currentSelectedContainer).size(WIDGET_SIZE);
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

    public void setData (@Null MilitaryGearSaveData gearSaveData, MilitaryGearSlot slot) {
        this.slot = slot;
        MilitaryGearsGameData gearsGameData = API.get(GameData.class).getMilitaryGearsGameData();

        if (gearSaveData == null) {
            gearTitle.setText("No " + slot.getStringValue() + " selected");
            gearStats.setData(null);
        } else {
            gearTitle.setText(gearsGameData.getGears().get(gearSaveData.getName()).getTitle());
            powerValueLabel.setText(String.valueOf(gearSaveData.getPower()));
            rarityValueLabel.setText(gearSaveData.getRarity().getStringValue());
            rarityValueLabel.setColor(Color.valueOf(gearSaveData.getRarity().getBackgroundColor()));
            typeValueLabel.setText(gearsGameData.getGears().get(gearSaveData.getName()).getType().getStringValue());
            gearStats.setData(gearSaveData.getStatsData());
        }
        currentSelectedContainer.setData(gearSaveData);
        equipButton.setText("Equip");
    }
}
