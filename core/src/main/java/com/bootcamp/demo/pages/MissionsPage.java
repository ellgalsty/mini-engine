package com.bootcamp.demo.pages;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.MilitaryGearSlot;
import com.bootcamp.demo.data.MissionsManager;
import com.bootcamp.demo.data.game.FlagGameData;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.PetGameData;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.dialogs.FlagDialog;
import com.bootcamp.demo.dialogs.GearDialog;
import com.bootcamp.demo.dialogs.PetDialog;
import com.bootcamp.demo.dialogs.TacticalDialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.events.EquipFlagEvent;
import com.bootcamp.demo.events.EquipGearEvent;
import com.bootcamp.demo.events.core.EventHandler;
import com.bootcamp.demo.events.core.EventListener;
import com.bootcamp.demo.events.core.EventModule;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.containers.GearContainer;
import com.bootcamp.demo.pages.containers.StarsContainer;
import com.bootcamp.demo.pages.containers.StatsContainer;
import com.bootcamp.demo.pages.containers.TacticalContainer;
import com.bootcamp.demo.pages.core.APage;
import lombok.Getter;

public class MissionsPage extends APage implements EventListener {
    private static final float WIDGET_SIZE = 225f;

    private StatsContainer statsContainer;
    private TacticalsContainer tacticalGearContainer;
    private GearsContainer gearsContainer;
    @Getter
    private FlagContainer flagContainer;
    @Getter
    private PetContainer petContainer;
    private LootLevelButton lootLevelButton;
    private LootButton lootButton;
    private AutoLootButton autoLootButton;

    @Override
    protected void constructContent (Table content) {
        final Table powerSegment = constructPowerSegment();
        final Table mainUISegment = constructMainUISegment();

        final Table gameUIOverlay = new Table();
        gameUIOverlay.setBackground(Resources.getDrawable("basics/white-pixel", ColorLibrary.get("e0945b")));
        gameUIOverlay.add(powerSegment).expand().bottom().size(700, 125);

        // assemble
        content.add(gameUIOverlay).grow();
        content.row();
        content.add(mainUISegment).growX();
    }

    private Table constructPowerSegment () {
        final Image atkIcon = new Image(Resources.getDrawable("ui/atk"));
        atkIcon.setScaling(Scaling.fit);

        // add inner table so that we have a border for the segment
        final Label label = Labels.make(GameFont.BOLD_28, ColorLibrary.get("fffdfc"));
        label.setText(String.valueOf(MissionsManager.calculateCumulativePower()));

        final Table inner = new Table();
        inner.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(ColorLibrary.get("998272")));
        inner.defaults().space(20);
        inner.add(atkIcon).size(Value.percentHeight(0.75f, inner));
        inner.add(label);

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35_TOP.getDrawable(ColorLibrary.get("fee5d6")));
        segment.add(inner).grow().pad(8, 8, 0, 8);
        return segment;
    }

    private Table constructMainUISegment () {
        final Table statsSegment = constructStatsSegment();
        final Table equipmentsSegment = constructEquipmentsSegment();
        final Table buttonsSegment = constructButtonsSegment();

        final Table segment = new Table();
        segment.setBackground(Resources.getDrawable("basics/white-pixel", ColorLibrary.get("f6e6de")));
        segment.pad(30).defaults().growX().space(30);
        segment.add(statsSegment);
        segment.row();
        segment.add(equipmentsSegment);
        segment.row();
        segment.add(buttonsSegment);
        return segment;
    }

    private Table constructStatsSegment () {
        statsContainer = new StatsContainer();

        final Table statsInfoButton = new BorderedTable();
        statsInfoButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("f4e7dc")));

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("ae9d90")));
        segment.pad(15).defaults().space(25);
        segment.add(statsContainer).growX();
        segment.add(statsInfoButton).size(100);
        return segment;
    }

    private Table constructEquipmentsSegment () {
        final Table gearSegment = constructGearSegment();
        final Table secondaryGearSegment = constructSecondaryGearSegment();

        final Table segment = new Table();
        segment.defaults().space(60).growX();
        segment.add(gearSegment);
        segment.add(secondaryGearSegment);
        return segment;
    }

    private Table constructSecondaryGearSegment () {
        final int space = 25;

        // left segment
        tacticalGearContainer = new TacticalsContainer();
        flagContainer = new FlagContainer();

        final Table tacticalFlagContainersWrapper = new Table();
        tacticalFlagContainersWrapper.defaults().space(space).size(WIDGET_SIZE);
        tacticalFlagContainersWrapper.add(tacticalGearContainer);
        tacticalFlagContainersWrapper.row();
        tacticalFlagContainersWrapper.add(flagContainer);

        // right segment
        petContainer = new PetContainer();

        final Table petButtonSegment = constructPetButtonSegment();
        petContainer.addActor(petButtonSegment);

        // assemble
        final Table segment = new Table();
        segment.defaults().space(space).growX();
        segment.add(tacticalFlagContainersWrapper);
        segment.add(petContainer).fillY().width(WIDGET_SIZE);
        return segment;
    }

    private Table constructPetButtonSegment () {
        final Image buttonImage = new Image(Resources.getDrawable("ui/secondarygears/proof-of-glory"));
        buttonImage.setScaling(Scaling.fit);

        final OffsetButton petButton = new OffsetButton(OffsetButton.Style.ORANGE_35) {
            @Override
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(buttonImage).size(Value.percentHeight(0.75f, container));
            }
        };

        final Table segment = new Table();
        segment.setFillParent(true);
        segment.add(petButton).expand().bottom().growX().height(125);
        return segment;
    }

    private Table constructGearSegment () {
        final Table incompleteSetSegment = constructIncompleteSetSegment();
        gearsContainer = new GearsContainer();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("c8c7c7")));
        segment.pad(15).defaults().space(15);
        segment.add(incompleteSetSegment).height(50).fillX();
        segment.row();
        segment.add(gearsContainer);
        return segment;
    }

    private Table constructIncompleteSetSegment () {
        final BorderedTable setInfoButton = new BorderedTable();

        final Table infoWrapper = new Table();
        infoWrapper.add(setInfoButton).expand().size(75).right();
        infoWrapper.setFillParent(true);

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_15.getDrawable(ColorLibrary.get("a19891")));
        segment.addActor(infoWrapper);

        return segment;
    }

    private Table constructButtonsSegment () {
        lootLevelButton = new LootLevelButton();
        lootButton = new LootButton();
        autoLootButton = new AutoLootButton();

        final Table lootButtonWrapper = new Table();
        lootButtonWrapper.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("bebab8")));
        lootButtonWrapper.add(lootButton).height(200).growX().expand().bottom();

        final Table segment = new Table();
        segment.defaults().uniform().bottom().growX().space(35);
        segment.add(lootLevelButton).height(200);
        segment.add(lootButtonWrapper).height(300);
        segment.add(autoLootButton).height(200);
        return segment;
    }

    @EventHandler
    public void onEquipGearEvent (EquipGearEvent event) {
        final MilitaryGearsSaveData gearsSaveData = API.get(SaveData.class).getMilitaryGearsSaveData();
        gearsContainer.setData(gearsSaveData);
    }

    @EventHandler
    public void onEquipFlagEvent (EquipFlagEvent event) {
        final FlagsSaveData flagsSaveData = API.get(SaveData.class).getFlagsSaveData();
        flagContainer.setData(flagsSaveData);
    }


    @Override
    public void show (Runnable onComplete) {
        super.show(onComplete);
        API.get(EventModule.class).registerListener(this);
        final SaveData saveData = API.get(SaveData.class);
        for (MilitaryGearSlot slot : MilitaryGearSlot.values) {
            MilitaryGearSaveData gearSaveData = saveData.getMilitaryGearsSaveData().getMilitaryGears().get(slot);
            if (gearSaveData != null) {
                saveData.getMilitaryGearsSaveData().getMilitaryGears().get(slot).setPower(MissionsManager.calculatePowerFromStats(gearSaveData.getStatsData()));
            }
        }
        statsContainer.setData(MissionsManager.getGeneralStatsData());
        tacticalGearContainer.setData(saveData.getTacticalsSaveData());
        gearsContainer.setData(saveData.getMilitaryGearsSaveData());
        flagContainer.setData(saveData.getFlagsSaveData());
        petContainer.setData(saveData.getPetsSaveData());
        lootLevelButton.setData();
        lootButton.setData();
        autoLootButton.setData();
    }

    public static class LootLevelButton extends OffsetButton {
        private final Label handleLevelLabel;
        private final Label gripLevelLabel;
        private final Image lootIcon;
        private final Table handleGripLevelWrapper;

        public LootLevelButton () {
            handleLevelLabel = Labels.make(GameFont.BOLD_20, ColorLibrary.get("fffae3"));
            gripLevelLabel = Labels.make(GameFont.BOLD_20, ColorLibrary.get("fffae3"));
            lootIcon = new Image();

            final Table handleLevelSegment = new Table();
            handleLevelSegment.setBackground(Squircle.SQUIRCLE_25.getDrawable(ColorLibrary.get("987f59")));
            handleLevelSegment.add(handleLevelLabel).expand().center();

            final Table gripLevelSegment = new Table();
            gripLevelSegment.setBackground(Squircle.SQUIRCLE_25.getDrawable(ColorLibrary.get("987f59")));
            gripLevelSegment.add(gripLevelLabel).expand().center();

            handleGripLevelWrapper = new Table();
            handleGripLevelWrapper.defaults().grow().space(10);
            handleGripLevelWrapper.add(handleLevelSegment);
            handleGripLevelWrapper.row();
            handleGripLevelWrapper.add(gripLevelSegment);

            build(OffsetButton.Style.ORANGE_35);
        }

        @Override
        protected void buildInner (Table container) {
            super.buildInner(container);
            container.pad(20).defaults().space(20);
            container.add(lootIcon).size(Value.percentHeight(0.5f, container)).padLeft(25);
            container.add(handleGripLevelWrapper).grow().padRight(20);
        }

        public void setData () {
            handleLevelLabel.setText("Lv.7");
            gripLevelLabel.setText("Lv.3");
            lootIcon.setDrawable(Resources.getDrawable("ui/capy-loot"));
        }
    }

    public static class LootButton extends OffsetButton {
        private final Label label;
        private final Image lootIcon;

        public LootButton () {
            label = Labels.make(GameFont.BOLD_22, ColorLibrary.get("fffae3"));
            lootIcon = new Image();

            build(Style.GREEN_35);
        }

        @Override
        protected void buildInner (Table container) {
            super.buildInner(container);
            container.pad(20).defaults().expand().right();
            container.add(label);
            container.add(lootIcon).size(Value.percentHeight(0.5f, container));
        }

        public void setData () {
            label.setText("LOOT");
            lootIcon.setDrawable(Resources.getDrawable("ui/capy-loot"));
        }
    }

    public static class AutoLootButton extends OffsetButton {
        private final Label label;
        private final Image lootIcon;

        public AutoLootButton () {
            label = Labels.make(GameFont.BOLD_22, ColorLibrary.get("fffae3"));
            lootIcon = new Image();

            build(Style.GREY_35);
        }

        @Override
        protected void buildInner (Table container) {
            super.buildInner(container);
            container.pad(20);
            container.add(label);
            container.add(lootIcon).size(Value.percentHeight(0.5f, container));
        }

        public void setData () {
            label.setText("Auto Loot");
            lootIcon.setDrawable(Resources.getDrawable("ui/capy-loot"));
        }
    }

    private static class TacticalsContainer extends BorderedTable {
        private final WidgetsContainer<TacticalContainer> tacticalWidgets = new WidgetsContainer<>(2);

        public TacticalsContainer () {
            super(2);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("bab9bb")));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get("bab9bb")));
            tacticalWidgets.pad(5).defaults().space(10).grow();

            for (int i = 0; i < 4; i++) {
                final TacticalContainer tacticalContainer = new TacticalContainer();
                tacticalWidgets.add(tacticalContainer);
            }
            add(tacticalWidgets).grow();
        }

        public void setData (TacticalsSaveData tacticalsSaveData) {
            final Array<TacticalContainer> widgets = tacticalWidgets.getWidgets();

            for (int i = 0; i < widgets.size; i++) {
                final TacticalContainer widget = widgets.get(i);
                final String equippedTacticalName = tacticalsSaveData.getEquippedTacticals().get(i);
                if (equippedTacticalName != null) {
                    final TacticalSaveData tacticalSaveData = tacticalsSaveData.getTacticals().get(equippedTacticalName);
                    widget.setData(tacticalSaveData);
                } else {
                    widget.setData(null);
                }
            }

            setOnClick(() -> {
                TacticalDialog dialog = API.get(DialogManager.class).getDialog(TacticalDialog.class);
                dialog.setData(tacticalsSaveData);
                API.get(DialogManager.class).show(TacticalDialog.class);
            });
        }
    }

    public static class FlagContainer extends BorderedTable {
        private final Image icon;
        private final StarsContainer starsContainer;

        public FlagContainer () {
            starsContainer = new StarsContainer();
            final Table overlay = constructOverlay();
            icon = new Image();
            icon.setScaling(Scaling.fit);

            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
            addActor(overlay);
        }

        public void setData (FlagsSaveData flagsSaveData) {
            if (flagsSaveData == null) {
                setEmpty();
                return;
            }
            final String equippedFlagName = flagsSaveData.getEquippedFlag();
            if (equippedFlagName != null) {
                setOnClick(() -> {
                    FlagDialog flagDialog = API.get(DialogManager.class).getDialog(FlagDialog.class);
                    flagDialog.setData(flagsSaveData);
                    API.get(DialogManager.class).show(FlagDialog.class);
                });
                final FlagSaveData equippedFlagSaveData = flagsSaveData.getFlags().get(equippedFlagName);
                final FlagGameData flagGameData = API.get(GameData.class).getFlagsGameData().getFlags().get(equippedFlagName);
                icon.setDrawable(flagGameData.getIcon());
                starsContainer.setData(flagsSaveData.getFlags().get(flagsSaveData.getEquippedFlag()).getStarCount());
                setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(equippedFlagSaveData.getRarity().getBackgroundColor())));
                setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get(equippedFlagSaveData.getRarity().getBorderColor())));
            } else {
                setOnClick(() -> {
                    FlagDialog flagDialog = API.get(DialogManager.class).getDialog(FlagDialog.class);
                    flagDialog.setData(flagsSaveData);
                    API.get(DialogManager.class).show(FlagDialog.class);
                });
                setEmpty();
            }
        }

        private Table constructOverlay () {
            final Table segment = new Table();
            segment.pad(10);
            segment.add(starsContainer).expand().top().left();
            segment.setFillParent(true);
            return segment;
        }
    }

    public static class PetContainer extends BorderedTable {
        private final Image icon;
        private final StarsContainer starsContainer;

        public PetContainer () {
            starsContainer = new StarsContainer();
            final Table overlay = constructOverlay();
            icon = new Image();
            icon.setScaling(Scaling.fit);

            add(icon).size(Value.percentWidth(0.75f, this), Value.percentHeight(0.75f, this));
            addActor(overlay);
        }

        public void setData (@Null PetsSaveData petsSaveData) {
            if (petsSaveData == null || petsSaveData.getEquippedPetId() == null) {
                setEmpty();
            } else {
                final String equippedPetName = petsSaveData.getEquippedPetId();
                final PetSaveData equippedPetSaveData = petsSaveData.getPets().get(equippedPetName);
                final PetGameData petGameData = API.get(GameData.class).getPetsGameData().getPets().get(equippedPetName);
                icon.setDrawable(petGameData.getIcon());
                starsContainer.setData(equippedPetSaveData.getStarCount());
                setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(equippedPetSaveData.getRarity().getBackgroundColor())));
                setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get(equippedPetSaveData.getRarity().getBorderColor())));
            }
            setOnClick(() -> {
                PetDialog petDialog = API.get(DialogManager.class).getDialog(PetDialog.class);
                petDialog.setData(petsSaveData);
                API.get(DialogManager.class).show(PetDialog.class);
            });
        }

        @Override
        public void setEmpty () {
            super.setEmpty();
            icon.setDrawable(null);
            setBackground((Drawable) null);
        }

        private Table constructOverlay () {
            final Table segment = new Table();
            segment.pad(10);
            segment.add(starsContainer).expand().top().left();
            segment.setFillParent(true);
            return segment;
        }
    }

    public static class GearsContainer extends WidgetsContainer<GearContainer> {
        private static final float WIDGET_SIZE = 225f;

        public GearsContainer () {
            super(3);
            defaults().space(25).size(WIDGET_SIZE);

            for (MilitaryGearSlot slot : MilitaryGearSlot.values) {
                final GearContainer gearContainer = new GearContainer(slot);
                add(gearContainer);
            }
        }

        private void setData (MilitaryGearsSaveData militaryGearsSaveData) {
            final Array<GearContainer> widgets = getWidgets();

            for (int i = 0; i < MilitaryGearSlot.values.length; i++) {
                final GearContainer widget = widgets.get(i);
                MilitaryGearSaveData gearSaveData = militaryGearsSaveData.getMilitaryGears().get(MilitaryGearSlot.values[i]);
                if (gearSaveData == null) {
                    widget.setEmpty();
                } else {
                    widget.setData(gearSaveData);
                }
                MilitaryGearSlot slot = MilitaryGearSlot.values[i];
                widget.setOnClick(() -> {
                    GearDialog gearDialog = API.get(DialogManager.class).getDialog(GearDialog.class);
                    gearDialog.setData(gearSaveData, slot);
                    API.get(DialogManager.class).show(GearDialog.class);
                });

            }
        }
    }
}
