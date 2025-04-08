package com.bootcamp.demo.pages;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.itemdata.Gear;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
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
        final Table flagContainer = new BorderedTable();

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

    private BorderedTable constructAnimalSegment () {
        final BorderedTable animalSegmentButton = new BorderedTable();
        animalSegmentButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("deb46e")));
        animalSegmentButton.setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get("988060")));

        final BorderedTable segment = new BorderedTable(0);
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("b29983")));
        segment.add(animalSegmentButton).expand().bottom().growX().height(100);
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

    private static class TacticalWidgetContainer extends Table {
        public TacticalWidgetContainer () {
            setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("6398c3")));
        }
    }

    private Table constructGearSegment () {
        final Table incompleteSetSegment = new Table();
        incompleteSetSegment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("a19891")));
        final Table gearsContainer = constructGearsContainer();

        final Table segment = new Table();
        segment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("c8c7c7")));
        segment.pad(20).defaults().growX().space(15);
        segment.add(incompleteSetSegment).height(50);
        segment.row();
        segment.add(gearsContainer);
        return segment;
    }

    private Table constructGearsContainer () {
        // TODO: use widget container
        // TODO: make an enum for slot types(with static values array), make a slot type widget, populate gear slots from slot enum
        final BorderedTable weaponContainer = new BorderedTable();
        final BorderedTable meleeContainer = new BorderedTable();
        final BorderedTable headContainer = new BorderedTable();
        final BorderedTable bodyContainer = new BorderedTable();
        final BorderedTable glovesContainer = new BorderedTable();
        final BorderedTable shoesContainer = new BorderedTable();

        final Table segment = new Table();
        segment.defaults().space(25).size(WIDGET_SIZE);
        segment.add(weaponContainer);
        segment.add(meleeContainer);
        segment.add(headContainer);
        segment.row();
        segment.add(bodyContainer);
        segment.add(glovesContainer);
        segment.add(shoesContainer);
        return segment;
    }

    private Table constructButtonsSegment () {
        final BorderedTable lootLevelButton = new BorderedTable();
        lootLevelButton.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("e3b26a")));
        lootLevelButton.setBorderDrawable(Resources.getDrawable("basics/white-squircle-border-35", ColorLibrary.get("988153")));
        final Table lootButton = constructLootButtonSegment();
        final BorderedTable autoLootButton = new BorderedTable();
        autoLootButton.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("acacac")));
        autoLootButton.setBorderDrawable(Resources.getDrawable("basics/white-squircle-border-35", ColorLibrary.get("828482")));

        final Table segment = new Table();
        segment.defaults().bottom().growX().space(35);
        segment.add(lootLevelButton).height(200);
        segment.add(lootButton).height(300);
        segment.add(autoLootButton).height(200);
        return segment;
    }

    private Table constructLootButtonSegment () {
        final BorderedTable lootButton = new BorderedTable();
        lootButton.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("97d382")));
        lootButton.setBorderDrawable(Resources.getDrawable("basics/white-squircle-border-35", ColorLibrary.get("6b9d54")));

        final Table segment = new Table();
        segment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("bcbbba")));
        segment.add(lootButton).expand().height(200).bottom().growX();
        return segment;
    }

    private Table constructStatsContainer () {           // TODO: create separate stat class
        // TODO: use widget container
        final Table segment = new Table();
        segment.defaults().growX().space(25).height(STAT_HEIGHT);

        for (int i = 0; i < DISPLAYED_STATS_COUNT; i++) {
            final Table statContainer = new Table();
            statContainer.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("4c403a")));
            segment.add(statContainer);
            if ((i + 1) % 3 == 0) {
                segment.row();
            }
        }
        return segment;
    }
}
