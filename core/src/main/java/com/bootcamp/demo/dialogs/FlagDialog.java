package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.MissionsManager;
import com.bootcamp.demo.data.game.FlagGameData;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.save.FlagSaveData;
import com.bootcamp.demo.data.save.FlagsSaveData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.dialogs.buttons.TextOffsetButton;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.CustomScrollPane;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.containers.StarsContainer;
import com.bootcamp.demo.pages.containers.StatsContainer;
import com.bootcamp.demo.presenters.WidgetLibrary;
import lombok.Getter;


public class FlagDialog extends ADialog {
    private static FlagContainer currentSelectedContainer;
    private static Label flagTitleLabel;
    private static StatsContainer flagStats;
    private OwnedFlagsContainer ownedFlags;
    private TextOffsetButton equipButton;

    @Override
    protected void constructContent (Table content) {
        final Table flagInfoWrapper = constructFlagInfoWrapper();
        final Table statsSegment = constructStatsSegment();
        final Table ownedFlagsSegment = constructOwnedFlagsSegment();
        equipButton = new TextOffsetButton(OffsetButton.Style.GREEN_35);
        equipButton.setOnClick(() -> {
            MissionsManager.equipFlag(currentSelectedContainer.getFlagSaveData());
        });

        content.pad(30).defaults().space(25).growX().uniformX().minWidth(1000);
        content.add(flagInfoWrapper);
        content.row();
        content.add(statsSegment).height(300);
        content.row();
        content.add(ownedFlagsSegment);
        content.row();
        content.add(equipButton).size(300, 200);
    }

    private Table constructOwnedFlagsSegment () {
        final Label ownedFlagsTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("2c1a12"));
        ownedFlagsTitle.setText("Owned flags");
        ownedFlags = new OwnedFlagsContainer();
        final CustomScrollPane ownedFlagsScrollPane = WidgetLibrary.verticalScrollPane(ownedFlags);

        final Table segment = new Table();
        segment.defaults().grow();
        segment.add(ownedFlagsTitle);
        segment.row();
        segment.add(ownedFlagsScrollPane).height(300);
        return segment;
    }

    private Table constructFlagInfoWrapper () {
        flagTitleLabel = Labels.make(GameFont.BOLD_24);
        currentSelectedContainer = new FlagContainer();

        final Table segment = new Table();
        segment.defaults().space(25);
        segment.add(flagTitleLabel).expand().left().padLeft(25);
        segment.row();
        segment.add(currentSelectedContainer).height(450).growX();
        segment.row();
        return segment;
    }

    private Table constructStatsSegment () {
        final Label statsTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("2c1a12"));
        statsTitle.setText("Flag stats");
        flagStats = new StatsContainer();
        flagStats.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("af9e90")));

        final Table segment = new Table();
        segment.defaults().space(25);
        segment.add(statsTitle);
        segment.row();
        segment.add(flagStats).grow();
        return segment;
    }

    public void setData (FlagsSaveData flagsSaveData) {
        equipButton.setText("Equip");

        if (flagsSaveData.getEquippedFlag() == null) {
            currentSelectedContainer.setData(null);
            flagTitleLabel.setText("No flag selected");
            flagTitleLabel.setColor(Color.valueOf("3c3533"));
            ownedFlags.setData(flagsSaveData);
            return;
        }

        FlagGameData equippedFlagGameData = API.get(GameData.class).getFlagsGameData().getFlagsMap().get(flagsSaveData.getEquippedFlag());
        FlagSaveData equippedFlagSaveData = flagsSaveData.getFlags().get(flagsSaveData.getEquippedFlag());

        currentSelectedContainer.setData(equippedFlagSaveData);
        flagTitleLabel.setText(equippedFlagGameData.getTitle());
        flagTitleLabel.setColor(Color.valueOf(equippedFlagSaveData.getRarity().getBackgroundColor()));
        ownedFlags.setData(flagsSaveData);
    }

    public static class OwnedFlagsContainer extends WidgetsContainer<FlagContainer> {
        private static final float WIDGET_SIZE = 200f;

        public OwnedFlagsContainer () {
            super(4);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bdbdbd")));
            pad(45).defaults().size(WIDGET_SIZE).space(25);

            for (int i = 0; i < API.get(SaveData.class).getFlagsSaveData().getFlags().size; i++) {
                final FlagContainer flagContainer = new FlagContainer();
                add(flagContainer);
            }
        }

        private void setData (FlagsSaveData flagsSaveData) {
            freeChildren();

            for (FlagSaveData flagSaveData : flagsSaveData.getFlags().values()) {
                final FlagContainer widget = Pools.obtain(FlagContainer.class);
                add(widget);
                widget.setData(flagSaveData);
            }
        }
    }

    public static class FlagContainer extends BorderedTable {
        private final Image icon;
        private final StarsContainer starsContainer;
        @Getter
        private FlagSaveData flagSaveData;

        public FlagContainer () {
            starsContainer = new StarsContainer();
            final Table overlay = constructOverlay();
            icon = new Image();
            icon.setScaling(Scaling.fit);

            add(icon).size(Value.percentWidth(0.75f, this), Value.percentHeight(0.75f, this));
            addActor(overlay);
        }

        private void setData (@Null FlagSaveData flagSaveData) {
            this.flagSaveData = flagSaveData;
            if (flagSaveData == null) {
                setEmpty();
                return;
            }
            final FlagGameData flagGameData = API.get(GameData.class).getFlagsGameData().getFlagsMap().get(flagSaveData.getName());
            icon.setDrawable(flagGameData.getIcon());
            starsContainer.setData(flagSaveData.getStarCount());
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(flagSaveData.getRarity().getBackgroundColor())));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get(flagSaveData.getRarity().getBorderColor())));
            setOnClick(() -> {
                currentSelectedContainer.setData(flagSaveData);
                flagStats.setData(flagSaveData.getStatsData());
                flagTitleLabel.setText(flagGameData.getTitle());
            });
        }

        private Table constructOverlay () {
            final Table segment = new Table();
            segment.pad(10);
            segment.add(starsContainer).expand().top().left();
            segment.setFillParent(true);
            return segment;
        }
    }

    @Override
    protected String getTitle () {
        return "Flags";
    }
}
