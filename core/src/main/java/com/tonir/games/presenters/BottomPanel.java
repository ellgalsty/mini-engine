package com.tonir.games.presenters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.tonir.games.presenters.utils.BorderedTable;
import com.tonir.games.utils.Resources;
import com.tonir.games.utils.Squircle;

public class BottomPanel extends Table {

    public BottomPanel () {
        setBackground(Resources.getDrawable("basics/white-pixel", Color.WHITE));

        defaults().size(250);
        for (int i = 0; i < 4; i++) {
            final Image icon = new Image(Resources.getDrawable("basics/white-pixel", Color.RED), Scaling.fit);
            final BorderedTable buttonTest = new BorderedTable();
            buttonTest.setBackground(Squircle.SQUIRCLE_35.getDrawable(Color.GRAY));
            buttonTest.setBorderDrawable(Squircle.SQUIRCLE_35_BORDER.getDrawable(Color.GREEN));
            buttonTest.add(icon).size(100);
            add(buttonTest).expand();
        }
    }
}
