package com.tonir.demo.presenters.pages;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tonir.demo.utils.presenters.widgets.pages.APage;

public class TestPage extends APage {
    @Override
    protected void constructContent (Table content) {
        debugAll();
    }
}
