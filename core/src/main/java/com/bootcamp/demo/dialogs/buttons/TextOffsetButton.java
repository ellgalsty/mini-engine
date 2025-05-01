package com.bootcamp.demo.dialogs.buttons;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.widgets.OffsetButton;
import com.bootcamp.demo.localization.GameFont;

public class TextOffsetButton extends OffsetButton {
    private final Label label;

    public TextOffsetButton (Style style) {
        label = Labels.make(GameFont.BOLD_24, ColorLibrary.get("4b4b4b"));
        build(style);
    }

    public TextOffsetButton (Style style, GameFont font) {
        label = Labels.make(font, ColorLibrary.get("4b4b4b"));
        build(style);
    }

    @Override
    protected void buildInner (Table container) {
        super.buildInner(container);
        container.pad(20);
        container.add(label);
    }

    public void setText (String text) {
        label.setText(text);
    }
}
