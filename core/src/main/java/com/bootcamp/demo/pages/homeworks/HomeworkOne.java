package com.bootcamp.demo.pages.homeworks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Resources;
import com.bootcamp.demo.pages.core.APage;

public class HomeworkOne extends APage {
    private static final float WIDGET_SIZE = 275f;
    @Override
    protected void constructContent (Table content) {
        final Table mainGrid = constructMainGrid(5, 2);

        content.add(mainGrid);
    }

    private Table constructMainGrid (int rows, int cols) {
        final Table segment = new Table();
        segment.setBackground(Resources.getDrawable("basics/white-squircle-35"));
        segment.pad(25);
        segment.defaults().space(25).size(WIDGET_SIZE);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                final Table miniSegment = new Table();
                miniSegment.setBackground(Resources.getDrawable("basics/white-squircle-35", ColorLibrary.get("c6c6c6")));

                segment.add(miniSegment);
            }
            segment.row();
        }
        return segment;
    }
}
