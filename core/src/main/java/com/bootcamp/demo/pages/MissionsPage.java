package com.bootcamp.demo.pages;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
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

    private BorderedTable constructAnimalSegment () {
        final PetWidget animalSegmentButton = new PetWidget();
        animalSegmentButton.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("deb46e")));
        animalSegmentButton.setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get("988060")));

        final BorderedTable segment = new BorderedTable(0);
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("b29983")));
        segment.add(animalSegmentButton).expand().bottom().growX().height(100);
        return segment;
    }

    @Override
    public void show (Runnable onComplete) {
        super.show(onComplete);
        statsContainer.setData();
        widgetContainer.setData();
        gearsContainer.setData();
    }

    public static class StatsContainer extends WidgetsList<StatWidget> {

        public StatsContainer () {
            super(3);
            defaults().space(25).growX().height(STAT_HEIGHT);

            for (int i = 0; i < 9; i++) {
                final StatWidget statContainer = new StatWidget();
                statContainer.setData();
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

    private static class TacticalsContainer extends WidgetsList<TacticalWidget> {

        public TacticalsContainer () {
            super(2);
            pad(15).defaults().space(10).grow();

            for (int i = 0; i < 4; i++) {
                final TacticalWidget tacticalContainer = new TacticalWidget();
                tacticalContainer.setData();
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

    public static class GearsContainer extends WidgetsList<GearWidget> {

        public GearsContainer () {
            super(3);
            defaults().space(25).size(WIDGET_SIZE);

            for (int i = 0; i < 6; i++) {
                final GearWidget gearContainer = new GearWidget();
                gearContainer.setData();
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
        private final Label.LabelStyle labelStyle;
        private Label label;

        public StatWidget () {
            labelStyle = new Label.LabelStyle(new BitmapFont(), ColorLibrary.get("4e4238"));
            label = new Label("Power:      10", labelStyle);
            add(label);
        }

        private void setData () {

        }
    }

    public static class TacticalWidget extends Table {
        public TacticalWidget () {
            setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("6398c3")));
        }

        private void setData () {

        }
    }

    public static class GearWidget extends BorderedTable {
        public GearWidget () {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get("6098c0")));
        }

        private void setData () {

        }
    }

    public static class PetWidget extends BorderedTable {
        public PetWidget () {
        }

        private void setData () {

        }
    }

    public static class FlagWidget extends BorderedTable {
        public FlagWidget () {
        }

        private void setData () {

        }
    }
}
