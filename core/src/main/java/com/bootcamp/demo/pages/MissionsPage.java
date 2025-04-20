package com.bootcamp.demo.pages;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.StatData;
import com.bootcamp.demo.data.StatType;
import com.bootcamp.demo.data.StatsData;
import com.bootcamp.demo.data.game.*;
import com.bootcamp.demo.data.save.*;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.core.APage;

public class MissionsPage extends APage {
    private static final float WIDGET_SIZE = 225f;

    private StatsContainer statsContainer;
    private TacticalsContainer tacticalGearContainer;
    private GearsContainer gearsContainer;
    private FlagContainer flagContainer;
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
        label.setText("30.3m");

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
        final Image buttonImage = new Image(Resources.getDrawable("ui/secondarygears/cactus-fighter"));
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

    public static class LootLevelButton extends OffsetButton {
        private Label handleLevelLabel;
        private Label gripLevelLabel;
        private final Image lootIcon;
        private Table handleGripLevelWrapper;

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

    // TODO: basically LootButton and AutoLootButton are the same, with different data,
    //  so when we have actual data we will deal accordingly:)
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

    @Override
    public void show (Runnable onComplete) {
        super.show(onComplete);
        final SaveData saveData = API.get(SaveData.class);
        final StatsData statsData = API.get(StatsData.class);
        statsContainer.setData(statsData);
        tacticalGearContainer.setData(saveData.getTacticalsSaveData());
        gearsContainer.setData(saveData.getMilitaryGearsSaveData());
        flagContainer.setData();
        petContainer.setData(saveData.getPetsSaveData());
        lootLevelButton.setData();
        lootButton.setData();
        autoLootButton.setData();
    }

    public static class StatsContainer extends WidgetsContainer<StatWidget> {
        private ObjectMap<Stat, StatWidget> stats = new ObjectMap<>();

        public StatsContainer () {
            super(3);
            padLeft(50).defaults().space(25).expand().left();

            for (int i = 0; i < Stat.values.length; i++) {
                final StatWidget statContainer = new StatWidget();
                add(statContainer);
                stats.put(Stat.values[i], statContainer);
            }
        }

        public void setData (StatsData statsData) {
            for (IntMap.Entry<StatData> statData : statsData.getStats()) {
                final StatWidget widget = stats.get(statData.value.getStat());
                widget.setData(statData.value);
            }
            for (ObjectMap.Entry<Stat, StatWidget> statEntry : stats) {
                if (statEntry.value.label.getText().isEmpty()) {
                    statEntry.value.setEmptyData(statEntry.key);
                }
            }
        }
    }

    private static class TacticalsContainer extends WidgetsContainer<TacticalContainer> {

        public TacticalsContainer () {
            super(2);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("bab9bb")));
            pad(15).defaults().space(10).grow();

            for (int i = 0; i < 4; i++) {
                final TacticalContainer tacticalContainer = new TacticalContainer();
                add(tacticalContainer);
            }
        }

        public void setData (TacticalsSaveData tacticalsSaveData) {
            final Array<TacticalContainer> widgets = getWidgets();

            for (int i = 0; i < widgets.size; i++) {
                final TacticalContainer widget = widgets.get(i);
                final TacticalSaveData tacticalSaveData = tacticalsSaveData.getTacticals().get(i);

                widget.setData(tacticalSaveData);
            }
        }
    }

    public static class GearsContainer extends WidgetsContainer<GearContainer> {

        public GearsContainer () {
            super(3);
            defaults().space(25).size(WIDGET_SIZE);

            for (MilitaryGearSlot slot : MilitaryGearSlot.values) {
                final GearContainer gearContainer = new GearContainer(slot);
                add(gearContainer);
            }
        }

        private void setData (@Null MilitaryGearsSaveData militaryGearsSaveData) {
            final Array<GearContainer> widgets = getWidgets();

            for (int i = 0; i < widgets.size; i++) {
                final GearContainer widget = widgets.get(i);
                final MilitaryGearSaveData militarySaveData = militaryGearsSaveData.getMilitaryGears().get(i);

                widget.setData(militarySaveData);
            }
        }
    }

    public static class StatWidget extends Table {
        private final Label label;
        private final int defaultValue = 0;

        public StatWidget () {
            label = Labels.make(GameFont.BOLD_20, ColorLibrary.get("4e4238"));
            add(label);
        }

        private void setData (@Null StatData statData) {
            if (statData == null) {
                setEmpty();
                return;
            }
            if (statData.getType() == StatType.NUMBER) {
                label.setText(statData.getStat() + ": " + statData.getStatNumber() + "k");
            } else {
                label.setText(statData.getStat() + ": " + statData.getStatNumber() + "%");
            }
        }

        private void setEmptyData (Stat stat) {
            label.setText(stat + ": " + defaultValue);
        }

        private void setEmpty () {
            label.setText("0");
        }
    }

    public static class TacticalContainer extends Table {
        private final Image icon;

        public TacticalContainer () {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("6398c3")));

            icon = new Image();
            icon.setScaling(Scaling.fit);
            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        }

        private void setData (@Null TacticalSaveData tacticalSaveData) {
            if (tacticalSaveData == null) {
                setEmpty();
                return;
            }
            final TacticalGameData tacticalGameData = API.get(GameData.class).getTacticalsGameData().getTacticals().get(tacticalSaveData.getName());
            icon.setDrawable(tacticalGameData.getIcon());
        }

        private void setEmpty () {
            icon.setDrawable(Resources.getDrawable("ui/secondarygears/ice-bubble"));
        }
    }

    public static class GearContainer extends BorderedTable {
        private final Image icon;
        private final MilitaryGearSlot slot;
        private Label levelLabel;
        private Label rankLabel;
        private final StarsContainer starsContainer;

        public GearContainer (MilitaryGearSlot slot) {
            this.slot = slot;
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("6098c0")));

            icon = new Image();
            icon.setScaling(Scaling.fit);
            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
            starsContainer = new StarsContainer();
            final Table overlay = constructOverlay();
            addActor(overlay);
        }

        private void setData (MilitaryGearSaveData militaryGearSaveData) {
            if (militaryGearSaveData == null) {
                setEmptyGear();
                return;
            }
            final MilitaryGearGameData militaryGameData = API.get(GameData.class).getMilitaryGearsGameData().getMilitarySlotsWithGears().get(slot).get(militaryGearSaveData.getName());
            icon.setDrawable(militaryGameData.getIcon());
            levelLabel.setText("Lv. " + militaryGearSaveData.getLevel());
            rankLabel.setText(militaryGearSaveData.getRank());
            starsContainer.setData(militaryGearSaveData.getStarCount());
        }

        private void setEmptyGear () {
            icon.setDrawable(Resources.getDrawable("ui/maingears/star-palochka"));
            levelLabel.setText("");
            rankLabel.setText("");
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

    public static class StarsContainer extends WidgetsContainer<StarWidget> {
        private int starCount;

        public StarsContainer () {
            super(4);
            defaults().space(5).size(30);
            for (int i = 0; i < starCount; i++) {
                final StarWidget star = new StarWidget();
                add(star);
            }
        }

        private void setData (int starCount) {
            this.starCount = starCount;
            final Array<StarWidget> widgets = getWidgets();
            for (StarWidget star : widgets) {
                star.setData();
            }
        }
    }

    public static class StarWidget extends Table {
        private Image star;

        public StarWidget () {
            star = new Image();
            add(star).size(30);
        }

        private void setData () {
            star.setDrawable(Resources.getDrawable("ui/star"));
        }
    }

    public static class PetContainer extends BorderedTable {
        private final Image icon;

        public PetContainer () {
            icon = new Image();
            icon.setScaling(Scaling.fit);
            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        }

        private void setData (@Null PetsSaveData petsSaveData) {
            if (petsSaveData == null) {
                setEmpty();
                return;
            }
            for (IntMap.Entry<PetSaveData> petSaveData : petsSaveData.getPets()) {
                if (petSaveData.value.isEquipped()) {
                    final PetGameData petGameData = API.get(GameData.class).getPetsGameData().getPets().get(petSaveData.value.getName());
                    icon.setDrawable(petGameData.getIcon());
                }
            }
        }
    }

    public static class FlagContainer extends BorderedTable {
        private final Image icon;

        public FlagContainer () {
            icon = new Image();
            icon.setScaling(Scaling.fit);
            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));

        }

        private void setData () {
            icon.setDrawable(Resources.getDrawable("ui/secondarygears/flag"));
        }
    }
}
