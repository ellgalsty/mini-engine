package com.bootcamp.demo.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.Scaling;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.game.GameData;
import com.bootcamp.demo.data.game.PetGameData;
import com.bootcamp.demo.data.save.PetSaveData;
import com.bootcamp.demo.data.save.PetsSaveData;
import com.bootcamp.demo.data.save.SaveData;
import com.bootcamp.demo.dialogs.core.ADialog;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.Squircle;
import com.bootcamp.demo.engine.widgets.BorderedTable;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;
import com.bootcamp.demo.managers.API;
import com.bootcamp.demo.pages.MissionsPage;
import com.bootcamp.demo.pages.containers.StarsContainer;
import com.bootcamp.demo.pages.containers.StatsContainer;
import com.bootcamp.demo.pages.core.PageManager;

public class PetDialog extends ADialog {
    private static PetContainer currentContainer;
    private Label petTitleLabel;
    private static StatsContainer petStats;
    private OwnedPetsContainer ownedPets;

    @Override
    protected void constructContent (Table content) {
        setTitle("Pets", Color.valueOf("494846"));
        final Table mainDialog = constructMainDialog();
        content.pad(30);
        content.add(mainDialog).width(1000);
    }

    private Table constructMainDialog () {
        final Table petInfoWrapper = constructPetInfoWrapper();
        final Table petStatsSegment = constructStatsSegment();
        final Table ownedPetsSegment = constructOwnedPetsSegment();

        final Table segment = new Table();
        segment.defaults().space(25).grow();
        segment.add(petInfoWrapper);
        segment.row();
        segment.add(petStatsSegment);
        segment.row();
        segment.add(ownedPetsSegment);
        return segment;
    }

    private Table constructOwnedPetsSegment () {
        final Label ownedPetsTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("2c1a12"));
        ownedPetsTitle.setText("Owned pets");
        ownedPets = new OwnedPetsContainer();

        final Table segment = new Table();
        segment.add(ownedPetsTitle);
        segment.row();
        segment.add(ownedPets);
        return segment;
    }

    private Table constructPetInfoWrapper () {
        currentContainer = new PetContainer();
        petTitleLabel = Labels.make(GameFont.BOLD_22, Color.valueOf("403937"));

        final Table segment = new Table();
        segment.add(petTitleLabel).expand().left();
        segment.row();
        segment.add(currentContainer).growX().height(450);
        return segment;
    }

    private Table constructStatsSegment () {
        final Label statsTitle = Labels.make(GameFont.BOLD_22, Color.valueOf("2c1a12"));
        statsTitle.setText("Pet Stats");
        Array<Stat> stats = new Array<>();
        stats.add(Stat.HP);
        stats.add(Stat.ATK);
        petStats = new StatsContainer(stats);
        petStats.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("af9e90")));

        final Table segment = new Table();
        segment.defaults().space(25);
        segment.add(statsTitle);
        segment.row();
        segment.add(petStats).grow();
        return segment;
    }

    public void setData (PetsSaveData petsSaveData) {
        if (petsSaveData.getEquippedPet() == null){
            currentContainer.setData(null);
            petTitleLabel.setText("No pet selected");
            ownedPets.setData(petsSaveData);
            return;
        }
        PetGameData equippedPetGameData = API.get(GameData.class).getPetsGameData().getPets().get(petsSaveData.getEquippedPet());
        PetSaveData equippedPetSaveData = petsSaveData.getPets().get(petsSaveData.getEquippedPet());

        currentContainer.setData(equippedPetSaveData);
        petTitleLabel.setText(equippedPetGameData.getTitle());
        petTitleLabel.setColor(Color.valueOf(equippedPetSaveData.getRarity().getBackgroundColor()));
        petStats.setData(equippedPetSaveData.getStatsData());
        ownedPets.setData(petsSaveData);
    }

    public static class OwnedPetsContainer extends WidgetsContainer<PetContainer> {
        private static final float WIDGET_SIZE = 200f;

        public OwnedPetsContainer () {
            super(4);
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.valueOf("bdbdbd")));
            pad(45).defaults().size(WIDGET_SIZE).space(25);

            for (int i = 0; i < API.get(SaveData.class).getPetsSaveData().getPets().size; i++) {
                final PetContainer petContainer = new PetContainer();
                add(petContainer);
            }
        }

        private void setData (PetsSaveData petsSaveData) {
            freeChildren();

            for (PetSaveData petSaveData: petsSaveData.getPets().values()) {
                final PetContainer widget = Pools.obtain(PetContainer.class);
                add(widget);
                widget.setData(petSaveData);
            }
        }
    }

    public static class PetContainer extends BorderedTable {
        private final Image icon;
        private final StarsContainer starsContainer;

        public PetContainer () {
            starsContainer = new StarsContainer();
            final Table overlay = constructOverlay();
            icon = new Image();
            icon.setScaling(Scaling.fit);

            add(icon).size(Value.percentWidth(0.75f, this), Value.percentHeight(0.75f, this));
            addActor(overlay);
        }

        private void setData (PetSaveData petSaveData) {
            if (petSaveData == null) {
                setEmpty();
                return;
            }
            final PetGameData petGameData = API.get(GameData.class).getPetsGameData().getPets().get(petSaveData.getName());
            icon.setDrawable(petGameData.getIcon());
            starsContainer.setData(petSaveData.getStarCount());
            setBackground(Squircle.SQUIRCLE_35.getDrawable(ColorLibrary.get(petSaveData.getRarity().getBackgroundColor())));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(ColorLibrary.get(petSaveData.getRarity().getBorderColor())));
            setOnClick(() -> {
                API.get(SaveData.class).getPetsSaveData().setEquippedPet(petSaveData.getName());
                currentContainer.setData(petSaveData);
                petStats.setData(petSaveData.getStatsData());
                API.get(PageManager.class).getPage(MissionsPage.class).getPetContainer().setData(API.get(SaveData.class).getPetsSaveData());
            });
        }

        private Table constructOverlay () {
            final Table segment = new Table();
            segment.pad(10).defaults().expand();
            segment.add(starsContainer).top().left();
            segment.setFillParent(true);
            return segment;
        }
    }
}
