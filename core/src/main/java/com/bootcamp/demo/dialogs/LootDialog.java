package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.MilitaryGearSlot;
import com.bootcamp.demo.data.MissionsManager;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.MilitaryGearGameData;
import com.bootcamp.demo.data.save.MilitaryGearSaveData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.dialogs.buttons.TextOffsetButton;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.containers.GearContainer;
import com.bootcamp.demo.pages.containers.StatsContainer;

public class LootDialog extends ADialog {
    private LootGearPreviewContainer currentGearPreviewContainer;
    private LootGearPreviewContainer droppedGearPreviewContainer;
    private TextOffsetButton equipButton;
    private TextOffsetButton dropButton;

    @Override
    protected void constructContent (Table content) {
        final Table currentGearSegment = constructCurrentGearSegment();
        final Table droppedGearSegment = constructDroppedGearSegment();
        dropButton = new TextOffsetButton(OffsetButton.Style.GREEN_35);
        dropButton.setText("Drop");
        equipButton = new TextOffsetButton(OffsetButton.Style.GREEN_35);
        equipButton.setText("Equip");

        final Table buttonsWrapper = new Table();
        buttonsWrapper.defaults().expand().size(400, 150);
        buttonsWrapper.add(dropButton).left();
        buttonsWrapper.add(equipButton).right();

        content.pad(25).defaults().space(20).growX().minWidth(1000);
        content.add(currentGearSegment);
        content.row();
        content.add(droppedGearSegment);
        content.row();
        content.add(buttonsWrapper);
    }

    private Table constructCurrentGearSegment () {
        final Label currentLabel = Labels.make(GameFont.BOLD_22, Color.valueOf("494846"), "Current");
        currentGearPreviewContainer = new LootGearPreviewContainer();

        final Table currentGearInfoWrapper = new Table();
        currentGearInfoWrapper.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("c0bab8")));
        currentGearInfoWrapper.pad(20);
        currentGearInfoWrapper.add(currentGearPreviewContainer).growX();

        final Table segment = new Table();
        segment.add(currentLabel);
        segment.row();
        segment.add(currentGearInfoWrapper).growX();
        return segment;
    }

    private Table constructDroppedGearSegment () {
        final Label droppedLabel = Labels.make(GameFont.BOLD_22, Color.valueOf("494846"), "Loot Dropped!");
        droppedGearPreviewContainer = new LootGearPreviewContainer();

        final Table droppedGearInfoWrapper = new Table();
        droppedGearInfoWrapper.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("c0bab8")));
        droppedGearInfoWrapper.pad(20);
        droppedGearInfoWrapper.add(droppedGearPreviewContainer).growX();

        final Table segment = new Table();
        segment.add(droppedLabel);
        segment.row();
        segment.add(droppedGearInfoWrapper).growX();

        return segment;
    }

    public void setData (MilitaryGearSaveData militaryGearSaveData) {
        final MilitaryGearSlot slot = militaryGearSaveData.getSlot();
        currentGearPreviewContainer.setData(API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears().get(slot));
        droppedGearPreviewContainer.setData(militaryGearSaveData);

        equipButton.setOnClick(() -> {
            MissionsManager.equipGear(militaryGearSaveData);
            API.get(DialogManager.class).hide(LootDialog.class);
            MissionsManager.updateStats();
        });

        dropButton.setOnClick(() -> {
            MissionsManager.dropItem();
            API.get(DialogManager.class).hide(LootDialog.class);
            MissionsManager.updateStats();
        });
    }

    public static class LootGearPreviewContainer extends Table {
        private final Label title;
        private final GearContainer gearContainer;
        private final StatsContainer statsContainer;
        private final Label rarityLabel;

        public LootGearPreviewContainer () {
            title = Labels.make(GameFont.BOLD_22);
            rarityLabel = Labels.make(GameFont.BOLD_22);
            gearContainer = new GearContainer();
            statsContainer = new StatsContainer();

            final Table gearContainerWrapper = new Table();
            gearContainerWrapper.add(gearContainer).size(300);
            gearContainerWrapper.row();
            gearContainerWrapper.add(rarityLabel);

            defaults().space(15);
            add(title).colspan(2);
            row();
            add(gearContainerWrapper);
            add(statsContainer).growX();
        }

        private void setData (MilitaryGearSaveData militaryGearSaveData) {
            final ObjectMap<String, MilitaryGearGameData> gearsGameData = API.get(GameData.class).getMilitaryGearsGameData().getGears();
            final MilitaryGearGameData militaryGearGameData = gearsGameData.get(militaryGearSaveData.getName());

            title.setText(militaryGearGameData.getTitle());
            rarityLabel.setText(militaryGearSaveData.getRarity().getStringValue());
            rarityLabel.setColor(Color.valueOf(militaryGearSaveData.getRarity().getBackgroundColor()));
            gearContainer.setData(militaryGearSaveData);
            statsContainer.setData(militaryGearSaveData.getStatsData());
        }
    }
}
