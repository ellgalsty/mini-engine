package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.MilitaryGearSlot;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.MilitaryGearGameData;
import com.bootcamp.demo.data.game.MilitaryGearsGameData;
import com.bootcamp.demo.data.save.MilitaryGearSaveData;
import com.bootcamp.demo.data.save.MilitaryGearsSaveData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.containers.GearContainer;
import com.bootcamp.demo.pages.containers.StatsContainer;

public class GearDialog extends ADialog {
    private static final float WIDGET_SIZE = 300f;
    private MilitaryGearSlot slot;
    private static GearContainer currentContainer;
    private Label rarityValueLabel;
    private Label typeValueLabel;
    private Label powerValueLabel;
    private Label gearTitle;
    private static StatsContainer gearStats;
    private OwnedSkinsContainer ownedSkins;

    @Override
    protected void constructContent (Table content) {
        setTitle("Gear Info", Color.valueOf("494846"));

        final Table mainDialog = constructMainDialog();
        content.pad(30);
        content.add(mainDialog).width(1000);
    }

    private Table constructMainDialog () {
        final Table gearInfoWrapper = constructGearInfoWrapper();
        final Table statsWrapper = constructStatsWrapper();
        final Table ownedSkinsWrapper = constructOwnedSkinsWrapper();

        final Table segment = new Table();
        segment.pad(25).defaults().space(25).grow();
        segment.add(gearInfoWrapper);
        segment.row();
        segment.add(statsWrapper);
        segment.row();
        segment.add(ownedSkinsWrapper);
        return segment;
    }

    private Table constructOwnedSkinsWrapper () {
        final Label skinsTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("2c1a12"));
        skinsTitle.setText("Owned Skins");
        ownedSkins = new OwnedSkinsContainer();

        final Table segment = new Table();
        segment.defaults().space(25);
        segment.add(skinsTitle);
        segment.row();
        segment.add(ownedSkins).grow();
        return segment;
    }

    private Table constructStatsWrapper () {
        final Label statsTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("2c1a12"));
        statsTitle.setText("Gear Stats");
        Array<Stat> stats = new Array<>();
        stats.add(Stat.HP);
        stats.add(Stat.ATK);
        gearStats = new StatsContainer(stats);
        gearStats.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("af9e90")));

        final Table segment = new Table();
        segment.defaults().space(25);
        segment.add(statsTitle);
        segment.row();
        segment.add(gearStats).grow();
        return segment;
    }

    private Table constructGearInfoWrapper () {
        gearTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("38302e"));
        currentContainer = new GearContainer(slot);
        final Table gearInfoSegment = constructGearInfoSegment();

        final Table segment = new Table();
        segment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bfbab8")));
        segment.pad(25).defaults().space(25);
        segment.add(gearTitle);
        segment.row();
        segment.add(currentContainer).size(WIDGET_SIZE);
        segment.add(gearInfoSegment).grow();
        return segment;
    }

    private Table constructGearInfoSegment () {
        final Label powerLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("403837"));
        powerLabel.setText("Power:");
        final Label rarityLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("403837"));
        rarityLabel.setText("Rarity:");
        final Label typeLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("403837"));
        typeLabel.setText("Type:");

        powerValueLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("fdf3eb"));
        rarityValueLabel = Labels.make(GameFont.BOLD_20);
        typeValueLabel = Labels.make(GameFont.BOLD_20, Color.valueOf("fdf3eb"));

        final Table segment = new Table();
        segment.defaults().expand();
        segment.add(powerLabel).left();
        segment.add(powerValueLabel).right();
        segment.row();
        segment.add(rarityLabel).left();
        segment.add(rarityValueLabel).right();
        segment.row();
        segment.add(typeLabel).left();
        segment.add(typeValueLabel).right();
        return segment;
    }

    public void setData (MilitaryGearSaveData gearSaveData, MilitaryGearSlot slot) {
        this.slot = slot;
        MilitaryGearsSaveData gearsSaveData = API.get(SaveData.class).getMilitaryGearsSaveData();
        MilitaryGearsGameData gearsGameData = API.get(GameData.class).getMilitaryGearsGameData();
        gearTitle.setText(gearsGameData.getGears().get(gearSaveData.getName()).getTitle());

        currentContainer.setData(gearSaveData);
        powerValueLabel.setText(String.valueOf(gearSaveData.getPower()));
        rarityValueLabel.setText(gearSaveData.getRarity().getStringValue());
        rarityValueLabel.setColor(Color.valueOf(gearSaveData.getRarity().getBackgroundColor()));
        typeValueLabel.setText(slot.getStringValue());
        gearStats.setData(gearSaveData.getStatsData());
        ownedSkins.setData(gearsSaveData);
    }

    public static class OwnedSkinsContainer extends WidgetsContainer<OwnedSkinContainer> {
        private static final float WIDGET_SIZE = 200f;

        public OwnedSkinsContainer () {
            super(4);
            defaults().space(25).size(WIDGET_SIZE);

            for (int i = 0; i < API.get(SaveData.class).getMilitaryGearsSaveData().getMilitaryGears().size; i++) {
                final OwnedSkinContainer gearContainer = new OwnedSkinContainer();
                add(gearContainer);
            }
        }

        private void setData (MilitaryGearsSaveData militaryGearsSaveData) {
            freeChildren();

            for (MilitaryGearSaveData gearSaveData : militaryGearsSaveData.getMilitaryGears().values()) {
                final OwnedSkinContainer widget = Pools.obtain(OwnedSkinContainer.class);
                add(widget);
                widget.setData(gearSaveData);
            }
        }
    }

    public static class OwnedSkinContainer extends BorderedTable {
        private final Image icon;
        private Label rankLabel;


        public OwnedSkinContainer () {
            final Table overlay = constructOverlay();
            icon = new Image();
            icon.setScaling(Scaling.fit);

            add(icon).size(Value.percentWidth(0.75f, this), Value.percentWidth(0.75f, this));
            addActor(overlay);
        }

        public void setData (MilitaryGearSaveData gearSaveData) {
            if (gearSaveData == null) {
                setEmpty();
                return;
            }
            final MilitaryGearGameData militaryGameData = API.get(GameData.class).getMilitaryGearsGameData().getGears().get(gearSaveData.getName());
            icon.setDrawable(militaryGameData.getIcon());
            rankLabel.setText(gearSaveData.getRank());
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(gearSaveData.getRarity().getBackgroundColor())));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get(gearSaveData.getRarity().getBorderColor())));
            setOnClick(() -> {
                currentContainer.setData(gearSaveData);
                gearStats.setData(gearSaveData.getStatsData());
            });
        }

        private Table constructOverlay () {
            rankLabel = Labels.make(GameFont.BOLD_20, ColorLibrary.get("fef4ee"));

            final Table segment = new Table();
            segment.pad(10);
            segment.add(rankLabel).expand().bottom().right();
            segment.setFillParent(true);
            return segment;
        }
    }
}
