package com.tonir.games.presenters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tonir.games.utils.Resources;

public class BottomPanel extends Table {

    public BottomPanel () {
        setBackground(Resources.getDrawable("basics/white-pixel", Color.WHITE));
    }
}
