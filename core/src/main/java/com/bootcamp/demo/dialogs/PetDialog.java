package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.PetGameData;
import com.bootcamp.demo.data.save.PetSaveData;
import com.bootcamp.demo.data.save.PetsSaveData;
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

public class PetDialog extends ADialog {
    private PetContainer equippedPetContainer;
    private Label rarityValueLabel;
    private Label petTitleLabel;
    private Label hpValueLabel;
    private Label atkValueLabel;
    private WidgetsContainer<PetContainer> petsSegment;

    @Override
    protected void constructContent (Table content) {
        setTitle("Pets", Color.valueOf("494846"));
        final Table mainDialog = constructMainDialog();
        content.pad(30);
        content.add(mainDialog).width(1000);
    }

    private Table constructMainDialog () {
        final Table petInfoSegment = constructPetInfoSegment();
        equippedPetContainer = new PetContainer();
        petsSegment = new WidgetsContainer<>(4);
        petsSegment.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bdbdbd")));
        petsSegment.pad(45).defaults().size(200).space(25);

        final Table segment = new Table();
        segment.pad(30).defaults().space(25);
        segment.add(petTitleLabel).expand().left();
        segment.row();
        segment.add(equippedPetContainer).height(450).growX();
        segment.row();
        segment.add(petInfoSegment).grow();
        segment.row();
        segment.add(petsSegment).grow();
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
        petTitleLabel = Labels.make(GameFont.BOLD_24);
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

    public void setData (PetsSaveData petsSaveData) {
        petsSegment.freeChildren();
        for (PetSaveData petSaveData : petsSaveData.getPets().values()) {
            final PetContainer widget = new PetContainer();
            widget.setOnClick(() -> {
                petsSaveData.setEquippedPet(petSaveData.getName());
                API.get(DialogManager.class).hide(PetDialog.class);
                API.get(PageManager.class).show(MissionsPage.class);
            });
            widget.setData(petSaveData);
            petsSegment.add(widget);
        }

        PetGameData equippedPetGameData = API.get(GameData.class).getPetsGameData().getPets().get(petsSaveData.getEquippedPet());
        PetSaveData equippedPetSaveData = petsSaveData.getPets().get(petsSaveData.getEquippedPet());

        equippedPetContainer.setData(equippedPetSaveData);
        petTitleLabel.setText(equippedPetGameData.getTitle());
        petTitleLabel.setColor(Color.valueOf(equippedPetSaveData.getRarity().getBackgroundColor()));
        hpValueLabel.setText(String.valueOf(equippedPetSaveData.getStatsData().getStats().get(Stat.HP).getStatNumber()));
        atkValueLabel.setText(String.valueOf(equippedPetSaveData.getStatsData().getStats().get(Stat.ATK).getStatNumber()));
        rarityValueLabel.setText(equippedPetSaveData.getRarity().getStringValue());
        rarityValueLabel.setColor(Color.valueOf(equippedPetSaveData.getRarity().getBackgroundColor()));
    }

    public static class PetContainer extends BorderedTable {
        private final Image icon;
        private final MissionsPage.StarsContainer starsContainer;

        public PetContainer () {
            starsContainer = new MissionsPage.StarsContainer();
            final Table overlay = constructOverlay();
            icon = new Image();
            icon.setScaling(Scaling.fit);

            add(icon).size(Value.percentWidth(0.75f, this), Value.percentHeight(0.75f, this));
            addActor(overlay);
        }

        private void setData (PetSaveData petSaveData) {
            final PetGameData petGameData = API.get(GameData.class).getPetsGameData().getPets().get(petSaveData.getName());
            icon.setDrawable(petGameData.getIcon());
            starsContainer.setData(petSaveData.getStarCount());
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(petSaveData.getRarity().getBackgroundColor())));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get(petSaveData.getRarity().getBorderColor())));
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
