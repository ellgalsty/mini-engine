package com.bootcamp.demo.pages;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsList;
import com.bootcamp.demo.pages.core.APage;

public class MissionsPage extends APage {
    private static final float STAT_HEIGHT = 50f;
    private static final float WIDGET_SIZE = 225f;
    private static final int DISPLAYED_STATS_COUNT = 9;

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
        final Table powerSegmentInner = new Table();
        powerSegmentInner.setBackground(Squircle.SQUIRCLE_25.getDrawable(ColorLibrary.get("998272")));

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_25.getDrawable(ColorLibrary.get("fee5d6")));

        segment.add(powerSegmentInner).expand().bottom().size(690, 115);
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
        final Table statsContainer = constructStatsContainer();
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
        final FlagContainer flagContainer = new FlagContainer();

        final Table tacticalFlagContainersWrapper = new Table();
        tacticalFlagContainersWrapper.defaults().space(space).size(WIDGET_SIZE);
        tacticalFlagContainersWrapper.add(tacticalGearContainer);
        tacticalFlagContainersWrapper.row();
        tacticalFlagContainersWrapper.add(flagContainer);

        // right segment
        final BorderedTable animalSegment = constructAnimalSegment();

        // assemble
        final Table segment = new Table();
        segment.defaults().space(space).growX();
        segment.add(tacticalFlagContainersWrapper);
        segment.add(animalSegment).fillY().width(WIDGET_SIZE);
        return segment;
    }


    private BorderedTable constructTacticalGearContainer () {
        final TacticalsWidgetsContainer widgetContainer = new TacticalsWidgetsContainer(2);

        for (int i = 0; i < 4; i++) {
            final TacticalWidgetContainer tacticalGear = new TacticalWidgetContainer();
            widgetContainer.setData(tacticalGear);
        }

        final BorderedTable container = new BorderedTable();
        container.add(widgetContainer).grow();
        return container;
    }

    private Table constructGearSegment () {
        final Table incompleteSetSegment = new Table();
        incompleteSetSegment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("a19891")));
        final Table gearsContainer = constructGearsContainer();

        final Table segment = new Table();
        segment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("c8c7c7")));
        segment.pad(20).defaults().space(15);
        segment.add(incompleteSetSegment).height(50).fillX();
        segment.row();
        segment.add(gearsContainer);
        return segment;
    }

    private Table constructGearsContainer () {
        // TODO: make an enum for slot types(with static values array), make a slot type widget, populate gear slots from slot enum
        final GearWidgetContainer weaponContainer = new GearWidgetContainer();
        final GearWidgetContainer meleeContainer = new GearWidgetContainer();
        final GearWidgetContainer headContainer = new GearWidgetContainer();
        final GearWidgetContainer bodyContainer = new GearWidgetContainer();
        final GearWidgetContainer glovesContainer = new GearWidgetContainer();
        final GearWidgetContainer shoesContainer = new GearWidgetContainer();

        final GearsWidgetsContainer segment = new GearsWidgetsContainer(3);
        segment.setData(weaponContainer);
        segment.setData(meleeContainer);
        segment.setData(headContainer);
        segment.setData(bodyContainer);
        segment.setData(glovesContainer);
        segment.setData(shoesContainer);
        return segment;
    }

    private Table constructButtonsSegment () {
        final OffsetButton lootLevelButton = new OffsetButton(OffsetButton.Style.ORANGE_35);
        final Table lootButton = constructLootButtonSegment();
        final OffsetButton autoLootButton = new OffsetButton(OffsetButton.Style.GREY_35);

        final Table segment = new Table();
        segment.defaults().bottom().growX().space(35);
        segment.add(lootLevelButton).height(200);
        segment.add(lootButton).height(300);
        segment.add(autoLootButton).height(200);
        return segment;
    }

    private Table constructLootButtonSegment () {
        final OffsetButton lootButton = new OffsetButton(OffsetButton.Style.GREEN_35);

        final Table segment = new Table();
        segment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("bcbbba")));
        segment.add(lootButton).expand().height(200).bottom().growX();
        return segment;
    }

    private Table constructStatsContainer () {
        final StatsWidgetsContainer statsSegment = new StatsWidgetsContainer(3);

        for (int i = 0; i < DISPLAYED_STATS_COUNT; i++) {
            final StatWidgetContainer statContainer = new StatWidgetContainer();
            statsSegment.setData(statContainer);
        }
        return statsSegment;
    }

    private BorderedTable constructAnimalSegment () {
        final PetWidgetContainer animalSegmentButton = new PetWidgetContainer();
        animalSegmentButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("deb46e")));
        animalSegmentButton.setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get("988060")));

        final BorderedTable segment = new BorderedTable(0);
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("b29983")));
        segment.add(animalSegmentButton).expand().bottom().growX().height(100);
        return segment;
    }

    // static widget container classes
    public static class StatsWidgetsContainer extends Table {
        private final WidgetsList<StatWidgetContainer> statsContainer;

        public StatsWidgetsContainer (int rows) {
            statsContainer = new WidgetsList<>(rows);
            statsContainer.defaults().space(25).growX().height(STAT_HEIGHT);
            add(statsContainer).grow();
        }

        public void setData (StatWidgetContainer stat) {
            statsContainer.add(stat);
            statsContainer.debugAll();
        }
    }

    private static class TacticalsWidgetsContainer extends Table {
        private final WidgetsList<TacticalWidgetContainer> tacticalsWidgetContainer;

        public TacticalsWidgetsContainer (int rows) {
            tacticalsWidgetContainer = new WidgetsList<>(rows);
            tacticalsWidgetContainer.pad(15).defaults().space(10).grow();
            add(tacticalsWidgetContainer).grow();
        }

        public void setData (TacticalWidgetContainer tacticalWidgetContainer) {
            tacticalsWidgetContainer.add(tacticalWidgetContainer);
        }
    }

    public static class GearsWidgetsContainer extends Table {
        private final WidgetsList<GearWidgetContainer> gearsWidgetContainer;

        public GearsWidgetsContainer (int rows) {
            gearsWidgetContainer = new WidgetsList<>(rows);
            gearsWidgetContainer.defaults().space(25).size(WIDGET_SIZE);
            add(gearsWidgetContainer);
        }

        private void setData (GearWidgetContainer gearWidgetContainer) {
            gearsWidgetContainer.add(gearWidgetContainer);
        }
    }

    public static class StatWidgetContainer extends Table {
        public StatWidgetContainer () {
            setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("4c403a")));
        }
    }

    private static class TacticalWidgetContainer extends Table {
        public TacticalWidgetContainer () {
            setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("6398c3")));
        }
    }

    private static class GearWidgetContainer extends BorderedTable {
        public GearWidgetContainer () {
        }
    }

    private static class PetWidgetContainer extends BorderedTable {
        public PetWidgetContainer () {
        }
    }

    private static class FlagContainer extends BorderedTable {
        public FlagContainer () {
        }
    }
}
