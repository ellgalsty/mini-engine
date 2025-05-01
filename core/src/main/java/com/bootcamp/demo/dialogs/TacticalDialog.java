package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.TacticalGameData;
import com.bootcamp.demo.data.game.TacticalsGameData;
import com.bootcamp.demo.data.save.TacticalSaveData;
import com.bootcamp.demo.data.save.TacticalsSaveData;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.containers.StarsContainer;

public class TacticalDialog extends ADialog {
    private EquippedTacticalsContainer equippedTacticals;

    @Override
    protected void constructContent (Table content) {
        setTitle("Tactical", Color.valueOf("fee7da"), Color.valueOf("799f6c"));

        final Table mainDialog = constructMainDialog();
        content.pad(30);
        content.add(mainDialog).width(1000);
    }

    private Table constructMainDialog () {
        final Table equippedTacticalsSegment = constructEquippedTacticalsSegment();
        final Table tacticalInfoSegment = constructTacticalInfoSegment();
        final Table ownedTacticalsSegment = constructOwnedTacticalsSegment();

        final Table segment = new Table();
        segment.defaults().space(25).grow();
        segment.add(equippedTacticalsSegment);
        segment.row();
        segment.add(tacticalInfoSegment);
        segment.row();
        segment.add(ownedTacticalsSegment);
        return segment;
    }

    private Table constructOwnedTacticalsSegment () {
        return new Table();
    }

    private Table constructTacticalInfoSegment () {
        return new Table();
    }

    private Table constructEquippedTacticalsSegment () {
        equippedTacticals = new EquippedTacticalsContainer();

        final Table segment = new Table();
        segment.defaults().space(25);
        segment.add(equippedTacticals);
        return segment;
    }

    public void setData (@Null TacticalsSaveData tacticalsSaveData) {
        TacticalsGameData tacticalsGameData = API.get(GameData.class).getTacticalsGameData();

        if (tacticalsSaveData == null) {

        } else {

        }
    }

    public static class EquippedTacticalsContainer extends WidgetsContainer<EquippedTacticalContainer> {
        private static final float WIDGET_SIZE = 225f;

        public EquippedTacticalsContainer () {
            super(5);
            pad(25).defaults().space(25).size(WIDGET_SIZE);
        }

        private void setData (TacticalsSaveData tacticalsSaveData) {
            freeChildren();

            for (TacticalSaveData tacticalSaveData: tacticalsSaveData.getTacticals().values()) {
                final EquippedTacticalContainer widget = Pools.obtain(EquippedTacticalContainer.class);
                add(widget);
                widget.setData(tacticalSaveData);
            }
        }
    }

    public static class EquippedTacticalContainer extends BorderedTable {
        private final Image icon;
        private final Label levelLabel;
        private final StarsContainer starsContainer;

        public EquippedTacticalContainer () {
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.valueOf("a8f782")));
            starsContainer = new StarsContainer();
            final Table tacticalOverlay = constructOverLay();
            icon = new Image();
            icon.setScaling(Scaling.fit);
            levelLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("ebe6d9"));

            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
            addActor(tacticalOverlay);
        }

        public void setData (@Null TacticalSaveData tacticalSaveData) {
            if (tacticalSaveData == null) {
                setEmpty();
                return;
            }
            final TacticalGameData tacticalGameData = API.get(GameData.class).getTacticalsGameData().getTacticals().get(tacticalSaveData.getName());
            levelLabel.setText("lv" + tacticalSaveData.getLevel());
            starsContainer.setData(tacticalSaveData.getStarCount());
            icon.setDrawable(tacticalGameData.getIcon());
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(tacticalSaveData.getRarity().getBackgroundColor())));
        }

        @Override
        public void setEmpty () {
            super.setEmpty();
            icon.setDrawable(null);
            levelLabel.setText("");
        }
        private Table constructOverLay () {
            final Table segment = new Table();
            segment.pad(10).defaults().expand();
            segment.add(starsContainer).top().right();
            segment.row();
            segment.add(levelLabel).top().left();
            segment.setFillParent(true);
            return segment;
        }
    }
}
