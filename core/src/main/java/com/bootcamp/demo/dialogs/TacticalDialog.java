package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.Pools;
import com.bootcamp.demo.data.MissionsManager;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.TacticalGameData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.data.save.TacticalSaveData;
import com.bootcamp.demo.data.save.TacticalsSaveData;
import com.bootcamp.demo.dialogs.buttons.TextOffsetButton;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.CustomScrollPane;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.containers.StatsContainer;
import com.bootcamp.demo.presenters.WidgetLibrary;
import com.bootcamp.demo.presenters.widgets.TacticalContainer;

public class TacticalDialog extends ADialog {
    private EquippedTacticalsContainer equippedTacticals;
    private OwnedTacticalsContainer ownedTacticals;
    private OwnedTacticalContainer currentSelectedContainer;
    private StatsContainer statsContainer;
    private TextOffsetButton equipButton;
    private Label rarityValueLabel;
    private Label selectedTacticalTitle;
    private boolean tacticalSelection = false;

    @Override
    protected void constructContent (Table content) {
        final Table tacticalInfoSegment = constructTacticalInfoSegment();
        equippedTacticals = new EquippedTacticalsContainer();
        ownedTacticals = new OwnedTacticalsContainer();
        ownedTacticals.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bfbab7")));
        final CustomScrollPane ownedTacticalsScrollPane = WidgetLibrary.verticalScrollPane(ownedTacticals);

        equipButton = new TextOffsetButton(OffsetButton.Style.GREEN_35);
        equipButton.setOnClick(() -> {
            final TacticalSaveData chosenTacticalSaveData = currentSelectedContainer.container.getTacticalSaveData();
            OrderedMap<Integer, String> equippedTacticalsMap = API.get(SaveData.class).getTacticalsSaveData().getEquippedTacticals();
            if (equippedTacticalsMap.containsValue(chosenTacticalSaveData.getName(), true)) {
                return;
            }
            if (equippedTacticalsMap.size < 4) {
                MissionsManager.equipTactical(currentSelectedContainer.container.getTacticalSaveData(), equippedTacticalsMap.size);
                equippedTacticals.setData(equippedTacticalsMap);
            } else {
                tacticalSelection = true;
            }
        });

        // assemble
        content.pad(30);
        content.defaults().space(25).growX();
        content.add(equippedTacticals);
        content.row();
        content.add(tacticalInfoSegment);
        content.row();
        content.add(ownedTacticalsScrollPane).height(350);
        content.row();
        content.add(equipButton).size(300, 200);
    }

    private Table constructTacticalInfoSegment () {
        selectedTacticalTitle = Labels.make(GameFont.BOLD_20, Color.valueOf("4b4543"));
        currentSelectedContainer = new OwnedTacticalContainer();
        final Table statsWrapper = constructStatsWrapper();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bfbab8")));
        segment.pad(20);
        segment.add(selectedTacticalTitle);
        segment.row();
        segment.add(currentSelectedContainer).size(200);
        segment.add(statsWrapper).growX().height(350);
        return segment;
    }

    private Table constructStatsWrapper () {
        statsContainer = new StatsContainer();

        rarityValueLabel = Labels.make(GameFont.BOLD_22);
        final Label rarityLabel = Labels.make(GameFont.BOLD_22, Color.valueOf("020000"));
        rarityLabel.setText("Rarity:");
        final Table rarityLabelWrapper = new Table();
        rarityLabelWrapper.defaults().space(20);
        rarityLabelWrapper.add(rarityLabel);
        rarityLabelWrapper.add(rarityValueLabel);


        final Table segment = new Table();
        segment.defaults().expand();
        segment.add(rarityLabelWrapper).top();
        segment.row();
        segment.add(statsContainer);
        return segment;
    }

    public void setData (TacticalsSaveData tacticalsSaveData) {
        equipButton.setText("Equip");

        if (tacticalsSaveData != null) {
            ownedTacticals.setData(tacticalsSaveData);
            equippedTacticals.setData(tacticalsSaveData.getEquippedTacticals());
        }
    }

    private class EquippedTacticalContainerWrapper extends Table {
        private final TacticalContainer container;

        public EquippedTacticalContainerWrapper () {
            container = new TacticalContainer();
            container.setOnClick(() -> {
                final TacticalSaveData tacticalSaveData = container.getTacticalSaveData();
                if (tacticalSelection) {
                    MissionsManager.equipTactical(currentSelectedContainer.container.getTacticalSaveData(), tacticalSaveData.getSlot());
                    tacticalSelection = false;
                    equippedTacticals.setData(API.get(SaveData.class).getTacticalsSaveData().getEquippedTacticals());
                    return;
                }
                API.get(DialogManager.class).getDialog(TacticalDialog.class).selectTactical(tacticalSaveData);
            });
            add(container).size(225);
        }

        public void setData (TacticalSaveData tacticalSaveData) {
            container.setData(tacticalSaveData);
        }

        public void setData (@Null String tacticalName) {
            if (tacticalName == null) {
                container.setEmpty();
                return;
            }
            container.setData(API.get(SaveData.class).getTacticalsSaveData().getTacticals().get(tacticalName));
        }
    }

    public static class OwnedTacticalContainer extends Table {
        private final TacticalContainer container;

        public OwnedTacticalContainer () {
            container = new TacticalContainer();
            container.setOnClick(() -> {
                final TacticalSaveData tacticalSaveData = container.getTacticalSaveData();
                API.get(DialogManager.class).getDialog(TacticalDialog.class).selectTactical(tacticalSaveData);
            });
            add(container).grow();
        }

        private void setData (TacticalSaveData tacticalSaveData) {
            container.setData(tacticalSaveData);
        }
    }

    private void selectTactical (TacticalSaveData tacticalSaveData) {
        final TacticalGameData tacticalGameData = API.get(GameData.class).getTacticalsGameData().getTacticalsMap().get(tacticalSaveData.getName());
        currentSelectedContainer.setData(tacticalSaveData);
        statsContainer.setData(tacticalSaveData.getStatsData());
        selectedTacticalTitle.setText(tacticalGameData.getTitle());
        rarityValueLabel.setText(tacticalSaveData.getRarity().toString());
        rarityValueLabel.setColor(Color.valueOf(tacticalSaveData.getRarity().getBackgroundColor()));
    }

    public class EquippedTacticalsContainer extends WidgetsContainer<EquippedTacticalContainerWrapper> {
        public EquippedTacticalsContainer () {
            super(4);
            pad(25).defaults().space(25).size(225);

            for (int i = 0; i < 4; i++) {
                final EquippedTacticalContainerWrapper widget = new EquippedTacticalContainerWrapper();
                add(widget);
            }
        }

        public void setData (OrderedMap<Integer, String> equippedTacticalsNames) {
            final Array<EquippedTacticalContainerWrapper> widgets = getWidgets();

            for (int i = 0; i < 4; i++) {
                widgets.get(i).setData(equippedTacticalsNames.get(i));
            }
        }
    }

    public static class OwnedTacticalsContainer extends WidgetsContainer<OwnedTacticalContainer> {
        public OwnedTacticalsContainer () {
            super(5);
            pad(25).defaults().space(25).size(175);
        }

        public void setData (TacticalsSaveData data) {
            freeChildren();
            for (TacticalSaveData tacticalData : data.getTacticals().values()) {
                OwnedTacticalContainer widget = Pools.obtain(OwnedTacticalContainer.class);
                add(widget);
                widget.setData(tacticalData);
            }
        }
    }

    @Override
    protected String getTitle () {
        return "Tactical";
    }
}
