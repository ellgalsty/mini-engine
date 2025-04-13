package com.bootcamp.demo.pages;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
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
    private TacticalsContainer widgetContainer;
    private GearsContainer gearsContainer;

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
        statsInfoButton.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("f4e7dc")));

        final Table segment = new Table();
        segment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("ae9d90")));
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
        final BorderedTable tacticalGearContainer = constructTacticalGearContainer();
        final FlagWidget flagContainer = new FlagWidget();
        flagContainer.setData();

        final Table tacticalFlagContainersWrapper = new Table();
        tacticalFlagContainersWrapper.defaults().space(space).size(WIDGET_SIZE);
        tacticalFlagContainersWrapper.add(tacticalGearContainer);
        tacticalFlagContainersWrapper.row();
        tacticalFlagContainersWrapper.add(flagContainer);

        // right segment
        final PetWidget petSegment = new PetWidget();
        petSegment.setData();

        final Table petButtonSegment = constructPetButtonSegment();
        petSegment.addActor(petButtonSegment);

        // assemble
        final Table segment = new Table();
        segment.defaults().space(space).growX();
        segment.add(tacticalFlagContainersWrapper);
        segment.add(petSegment).fillY().width(WIDGET_SIZE);
        return segment;
    }

    private Table constructPetButtonSegment () {
        final OffsetButton petButton = new OffsetButton(OffsetButton.Style.ORANGE_35);

        final Table segment = new Table();
        segment.setFillParent(true);
        segment.add(petButton).expand().bottom().growX().height(125);
        return segment;
    }

    // TODO: 13.04.25 remove this redundant method
    private BorderedTable constructTacticalGearContainer () {
        widgetContainer = new TacticalsContainer();

        final BorderedTable container = new BorderedTable();
        container.add(widgetContainer).grow();
        return container;
    }

    private Table constructGearSegment () {
        final Table incompleteSetSegment = new Table();
        incompleteSetSegment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("a19891")));
        gearsContainer = new GearsContainer();

        final Table segment = new Table();
        segment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("c8c7c7")));
        segment.pad(20).defaults().space(15);
        segment.add(incompleteSetSegment).height(50).fillX();
        segment.row();
        segment.add(gearsContainer);
        return segment;
    }
    // TODO: make an enum for slot types(with static values array), make a slot type widget, populate gear slots from slot enum

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
        segment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("bcbbba")));
        segment.add(lootButton).expand().height(200).bottom().growX();
        return segment;
    }

    @Override
    public void show (Runnable onComplete) {
        super.show(onComplete);
        statsContainer.setData();
        widgetContainer.setData();
        gearsContainer.setData();
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

    private static class TacticalsContainer extends WidgetsContainer<TacticalWidget> {

        public TacticalsContainer () {
            super(2);
            pad(15).defaults().space(10).grow();

            for (int i = 0; i < 4; i++) {
                final TacticalWidget tacticalContainer = new TacticalWidget();
                add(tacticalContainer);
            }
        }

        public void setData () {
            final Array<TacticalWidget> widgets = getWidgets();

            for (TacticalWidget widget : widgets) {
                widget.setData();
            }
        }
    }

    public static class GearsContainer extends WidgetsContainer<GearWidget> {

        public GearsContainer () {
            super(3);
            defaults().space(25).size(WIDGET_SIZE);

            for (int i = 0; i < 6; i++) {
                final GearWidget gearContainer = new GearWidget();
                add(gearContainer);
            }
        }

        private void setData () {
            final Array<GearWidget> widgets = getWidgets();
            for (GearWidget widget : widgets) {
                widget.setData();
            }
        }
    }

    public static class StatWidget extends Table {
        private final Label label;

        public StatWidget () {
            label = Labels.make(GameFont.BOLD_40, ColorLibrary.get("4e4238"));
            add(label);
        }

        private void setData () {
            label.setText("Power: 124k");
        }
    }

    // TODO: 13.04.25 changed this as an example, do the same for the rest
    public static class TacticalWidget extends Table {
        private final Image icon;

        public TacticalWidget () {
            setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("6398c3")));

            icon = new Image();
            icon.setScaling(Scaling.fit);
            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        }

        private void setData () {
            icon.setDrawable(Resources.getDrawable("ui/secondarygears/ice-bubble"));
        }
    }

    public static class GearWidget extends BorderedTable {
        public GearWidget () {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("6098c0")));
        }

        private void setData () {
            Image icon = new Image(Resources.getDrawable("ui/maingears/star-palochka"));
            icon.setScaling(Scaling.fit);
            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        }
    }

    public static class PetWidget extends BorderedTable {
        public PetWidget () {
        }

        private void setData () {
            Image petIcon = new Image(Resources.getDrawable("ui/secondarygears/susu"));
            petIcon.setScaling(Scaling.fit);
            add(petIcon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        }
    }

    public static class FlagWidget extends BorderedTable {
        public FlagWidget () {
        }

        private void setData () {
            Image flagIcon = new Image(Resources.getDrawable("ui/secondarygears/flag"));
            flagIcon.setScaling(Scaling.fit);
            add(flagIcon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
        }
    }

    // TODO: 13.04.25 maybe remove TacticalWidget, GearWidget, FlagWidget, PetWidget into TacticalContainer, GearContainer, FlagContainer, PetContainer
}
