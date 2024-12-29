package com.tonir.demo.presenters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.tonir.demo.events.GameStartedEvent;
import com.tonir.demo.managers.API;
import com.tonir.demo.managers.PageManager;
import com.tonir.demo.managers.event.EventHandler;
import com.tonir.demo.managers.event.EventListener;
import com.tonir.demo.presenters.pages.TestPage;
import com.tonir.demo.presenters.utils.BorderedTable;
import com.tonir.demo.presenters.utils.pages.APage;
import com.tonir.demo.utils.Resources;
import com.tonir.demo.utils.Squircle;
import lombok.Setter;

public class BottomPanel extends Table implements EventListener {

    private final Array<BottomButton> buttons;
    private BottomButton currentButton;

    public BottomPanel () {
        registerListener();

        setBackground(Resources.getDrawable("basics/white-pixel", Color.WHITE));

        buttons = new Array<>();

        defaults().size(250).expandX();
        for (int i = 0; i < 4; i++) {
            // init button
            final BottomButton bottomButton = new BottomButton();
            buttons.add(bottomButton);

            // add to panel
            add(bottomButton);
        }
    }

    @EventHandler
    public void onGameStartedEvent (GameStartedEvent event) {
        buttons.get(0).setPageClass(TestPage.class);
        buttons.get(1).setPageClass(null);

        // by default select button 1
        buttons.get(1).select();
    }

    private class BottomButton extends BorderedTable {
        @Setter
        private Class<? extends APage> pageClass;
        @Setter
        private boolean isSelected;

        public BottomButton () {
            setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.GRAY));
            setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.GREEN));

            final Image icon = new Image(Resources.getDrawable("basics/white-pixel", Color.RED), Scaling.fit);
            add(icon).size(100);

            setOnClick(() -> {
                if (isSelected) {
                    deselect();
                } else {
                    select();
                }
            });
        }

        public void select () {
            // update current button
            if (currentButton != null) {
                currentButton.deselect();
            }
            currentButton = this;

            // select
            isSelected = true;
            // show page if any page exists otherwise just hide
            final PageManager pageManager = API.get(PageManager.class);
            if (pageClass == null) {
                pageManager.hidePage();
            } else {
                pageManager.showPage(pageClass);
            }

            visuallySelect();
        }

        public void deselect () {
            isSelected = false;
            API.get(PageManager.class).hidePage();

            visuallyDeselect();
        }

        private void visuallySelect () {

        }

        private void visuallyDeselect () {

        }
    }
}
