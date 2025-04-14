package com.bootcamp.demo.pages;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.game.MilitaryGearSlot;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.pages.core.APage;

public class MissionsPage extends APage {

    private static final float STAT_HEIGHT = 50f;
    private static final float WIDGET_SIZE = 225f;

    private StatsContainer statsContainer;
    private TacticalsContainer tacticalGearContainer;
    private GearsContainer gearsContainer;
    private FlagContainer flagContainer;
    private PetContainer petContainer;

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
        final Table inner = new Table();
        inner.setBackground(Squircle.SQUIRCLE_25.getDrawable(ColorLibrary.get("998272")));
        inner.add(atkIcon).size(Value.percentHeight(0.75f, inner));

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_25.getDrawable(ColorLibrary.get("fee5d6")));
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
        // TODO: 13.04.25 make nested classes for each button
        final Image lootButtonIcon = new Image(Resources.getDrawable("ui/capy-loot"));
        lootButtonIcon.setScaling(Scaling.fit);
        final OffsetButton lootLevelButton = new OffsetButton(OffsetButton.Style.ORANGE_35) {
            @Override
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(lootButtonIcon).size(Value.percentHeight(0.6f, container));
            }
        };

        final Table lootButton = constructLootButtonSegment();

        final Image autoLootIcon = new Image(Resources.getDrawable("ui/capy-loot"));
        final OffsetButton autoLootButton = new OffsetButton(OffsetButton.Style.GREY_35) {
            @Override
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(autoLootIcon).size(Value.percentHeight(0.6f, container));
            }
        };

        final Table segment = new Table();
        segment.defaults().bottom().growX().space(35);
        segment.add(lootLevelButton).height(200);
        segment.add(lootButton).height(300);
        segment.add(autoLootButton).height(200);
        return segment;
    }

    private Table constructLootButtonSegment () {
        final Image lootButtonIcon = new Image(Resources.getDrawable("ui/capy-loot"));
        lootButtonIcon.setScaling(Scaling.fit);

        final OffsetButton lootButton = new OffsetButton(OffsetButton.Style.GREEN_35) {
            @Override
            protected void buildInner (Table container) {
                super.buildInner(container);
                container.add(lootButtonIcon).size(Value.percentHeight(0.6f, container));
            }
        };

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("bcbbba")));
        segment.add(lootButton).expand().height(200).bottom().growX();
        return segment;
    }

    @Override
    public void show (Runnable onComplete) {
        super.show(onComplete);
        statsContainer.setData();
        tacticalGearContainer.setData();
        gearsContainer.setData();
        flagContainer.setData();
        petContainer.setData();

    }

    public static class StatsContainer extends WidgetsContainer<StatWidget> {

        public StatsContainer () {
            super(3);
            defaults().space(25).growX().height(STAT_HEIGHT);

            for (int i = 0; i < 9; i++) {
                final StatWidget statContainer = new StatWidget();
                add(statContainer);
            }
        }

        public void setData () {
            final Array<StatWidget> widgets = getWidgets();
            for (StatWidget widget : widgets) {
                widget.setData();
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

        public void setData () {
            final Array<TacticalContainer> widgets = getWidgets();

            for (TacticalContainer widget : widgets) {
                widget.setData();
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

        private void setData () {
            final Array<GearContainer> widgets = getWidgets();
            for (GearContainer widget : widgets) {
                widget.setData();
            }
        }
    }

    public static class StatWidget extends Table {
        private final Label label;

        public StatWidget () {
            label = Labels.make(GameFont.BOLD_24, ColorLibrary.get("4e4238"));
            add(label);
        }

        private void setData () {
            label.setText("Power: 124k");
        }
    }

    public static class TacticalContainer extends Table {
        private final Image icon;

        public TacticalContainer () {
            setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("6398c3")));

            icon = new Image();
            icon.setScaling(Scaling.fit);
            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        }

        private void setData () {
            icon.setDrawable(Resources.getDrawable("ui/secondarygears/ice-bubble"));
        }
    }

    public static class GearContainer extends BorderedTable {
        private final Image icon;
        private final MilitaryGearSlot slot;
        private Label levelLabel;
        private Label rankLabel;
        private StarsContainer starsContainer;
        private Table overlay = new Table();

        public GearContainer (MilitaryGearSlot slot) {
            this.slot = slot;
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("6098c0")));

            icon = new Image();
            icon.setScaling(Scaling.fit);
            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
            starsContainer = new StarsContainer();
            overlay = constructOverlay();
            addActor(overlay);
        }

        private void setData () {
            icon.setDrawable(Resources.getDrawable("ui/maingears/star-palochka"));
            levelLabel.setText("Lv.9");
            rankLabel.setText("A");
            starsContainer.setData();
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
        private int starCount = 2;

        public StarsContainer () {
            super(4);
            defaults().space(5).size(30);
            for (int i = 0; i < starCount; i++) {
                final StarWidget star = new StarWidget();
                add(star);
            }
        }

        private void setData () {
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

        private void setData () {
            icon.setDrawable(Resources.getDrawable("ui/secondarygears/susu"));
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
