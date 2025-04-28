package com.bootcamp.demo.pages.containers;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Null;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.StatData;
import com.bootcamp.demo.data.StatType;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.localization.GameFont;

public class StatWidget extends Table {
    protected final Label label;
    private final int defaultValue = 0;

    public StatWidget () {
        label = Labels.make(GameFont.BOLD_20, ColorLibrary.get("4e4238"));
        add(label);
    }

    protected void setData (@Null StatData statData) {
        if (statData == null) {
            return;
        }
        if (statData.getType() == StatType.NUMBER) {
            label.setText(statData.getStat() + ": " + statData.getStatNumber());
        } else {
            label.setText(statData.getStat() + ": " + statData.getStatNumber() + "%");
        }
    }

    protected void setEmpty (Stat stat) {
        label.setText(stat + ": " + defaultValue + "%");
    }
}
