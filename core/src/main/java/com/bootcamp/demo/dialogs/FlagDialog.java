package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.game.FlagGameData;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.save.FlagSaveData;
import com.bootcamp.demo.data.save.FlagsSaveData;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.dialogs.core.DialogManager;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.MissionsPage;
import com.bootcamp.demo.pages.core.PageManager;


public class FlagDialog extends ADialog {
    private FlagContainer equippedFlagContainer;
    private Label rarityValueLabel;
    private Label flagTitleLabel;
    private Label hpValueLabel;
    private Label atkValueLabel;
    private WidgetsContainer<FlagContainer> flagsSegment;

    @Override
    protected void constructContent (Table content) {
        setTitle("Flags", Color.valueOf("494846"));
        final Table mainDialog = constructMainDialog();
        content.pad(30);
        content.add(mainDialog).width(1000);
    }

    private Table constructMainDialog () {
        final Table petInfoSegment = constructPetInfoSegment();
        equippedFlagContainer = new FlagContainer();
        flagsSegment = new WidgetsContainer<>(4);
        flagsSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bdbdbd")));
        flagsSegment.pad(45).defaults().size(200).space(25);

        final Table segment = new Table();
        segment.pad(30).defaults().space(25);
        segment.add(flagTitleLabel).expand().left();
        segment.row();
        segment.add(equippedFlagContainer).height(450).growX();
        segment.row();
        segment.add(petInfoSegment).grow();
        segment.row();
        segment.add(flagsSegment).grow();
        return segment;
    }

    private Table constructPetInfoSegment () {
        final Label hpLabel = Labels.make(GameFont.BOLD_22, Color.valueOf("403837"));
        hpLabel.setText("HP:");
        final Label atkLabel = Labels.make(GameFont.BOLD_22, Color.valueOf("403837"));
        atkLabel.setText("ATK:");
        final Label rarityLabel = Labels.make(GameFont.BOLD_22, Color.valueOf("403837"));
        rarityLabel.setText("Rarity:");

        rarityValueLabel = Labels.make(GameFont.BOLD_22);
        flagTitleLabel = Labels.make(GameFont.BOLD_24);
        hpValueLabel = Labels.make(GameFont.BOLD_22, Color.valueOf("fdf3eb"));
        atkValueLabel = Labels.make(GameFont.BOLD_22, Color.valueOf("fdf3eb"));

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bfbab8c")));
        segment.pad(30).defaults().expand();

        segment.add(atkLabel).left();
        segment.add(atkValueLabel).right();
        segment.row();
        segment.add(hpLabel).left();
        segment.add(hpValueLabel).right();
        segment.row();
        segment.add(rarityLabel).left();
        segment.add(rarityValueLabel).right();
        return segment;
    }

    public void setData (FlagsSaveData flagsSaveData) {

        flagsSegment.freeChildren();
        for (FlagSaveData flagSaveData : flagsSaveData.getFlags().values()) {
            final FlagContainer widget = new FlagContainer();
            widget.setOnClick(() -> {
                flagsSaveData.setEquippedFlag(flagSaveData.getName());
                API.get(DialogManager.class).hide(FlagDialog.class);
                API.get(PageManager.class).show(MissionsPage.class);
            });
            widget.setData(flagSaveData);
            flagsSegment.add(widget);
        }

        if (flagsSaveData.getEquippedFlag() == null) {
            equippedFlagContainer.setData(null);
            flagTitleLabel.setText("No equipped flag");
        } else {
            FlagGameData equippedFlagGameData = API.get(GameData.class).getFlagsGameData().getFlags().get(flagsSaveData.getEquippedFlag());
            FlagSaveData equippedFlagSaveData = flagsSaveData.getFlags().get(flagsSaveData.getEquippedFlag());

            equippedFlagContainer.setData(equippedFlagSaveData);
            flagTitleLabel.setText(equippedFlagGameData.getTitle());
            flagTitleLabel.setColor(Color.valueOf(equippedFlagSaveData.getRarity().getBackgroundColor()));
            hpValueLabel.setText(String.valueOf(equippedFlagSaveData.getStatsData().getStats().get(Stat.HP).getStatNumber()));
            atkValueLabel.setText(String.valueOf(equippedFlagSaveData.getStatsData().getStats().get(Stat.ATK).getStatNumber()));
            rarityValueLabel.setText(equippedFlagSaveData.getRarity().getStringValue());
            rarityValueLabel.setColor(Color.valueOf(equippedFlagSaveData.getRarity().getBackgroundColor()));
        }

    }

    public static class FlagContainer extends BorderedTable {
        private final Image icon;
        private final MissionsPage.StarsContainer starsContainer;

        public FlagContainer () {
            starsContainer = new MissionsPage.StarsContainer();
            final Table overlay = constructOverlay();
            icon = new Image();
            icon.setScaling(Scaling.fit);

            add(icon).size(Value.percentWidth(0.75f, this), Value.percentHeight(0.75f, this));
            addActor(overlay);
        }

        private void setData (@Null FlagSaveData flagSaveData) {
            if (flagSaveData == null) {
                setEmpty();
                return;
            }
            final FlagGameData flagGameData = API.get(GameData.class).getFlagsGameData().getFlags().get(flagSaveData.getName());
            icon.setDrawable(flagGameData.getIcon());
            starsContainer.setData(flagSaveData.getStarCount());
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(flagSaveData.getRarity().getBackgroundColor())));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get(flagSaveData.getRarity().getBorderColor())));
        }

        private Table constructOverlay () {
            final Table segment = new Table();
            segment.pad(10);
            segment.add(starsContainer).expand().top().left();
            segment.setFillParent(true);
            return segment;
        }
    }
}
