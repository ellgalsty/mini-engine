package com.bootcamp.demo.pages.containers;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.*;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.StatData;
import com.bootcamp.demo.data.StatType;
import com.bootcamp.demo.data.StatsData;
import com.bootcamp.demo.engine.ColorLibrary;
import com.bootcamp.demo.engine.Labels;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;
import com.bootcamp.demo.localization.GameFont;

public class StatsContainer extends WidgetsContainer<StatsContainer.StatWidget> {

    public StatsContainer () {
        super(3);
        defaults().space(25).expand();
    }

    public void setData (StatsData statsData) {
        freeChildren();
        if (statsData == null) {
            return;
        }

        final OrderedMap<Stat, StatData> statsMap = statsData.getStats();
        for (ObjectMap.Entry<Stat, StatData> entry : statsMap) {
            final StatData statData = entry.value;
            // don't show empty stats
            if (statData.getValue() == 0) continue;

            final StatWidget widget = Pools.obtain(StatWidget.class);
            widget.setData(statData);
            add(widget);
        }
    }

    public static class StatWidget extends Table {
        protected final Label label;
        protected final Label valueLabel;

        public StatWidget () {
            label = Labels.make(GameFont.BOLD_18, ColorLibrary.get("4e4238"));
            valueLabel = Labels.make(GameFont.BOLD_18, ColorLibrary.get("4e4238"));

            final Table labelWrapper = new Table();
            labelWrapper.defaults().expand().space(10);
            labelWrapper.add(label);
            labelWrapper.add(valueLabel).right();

            add(labelWrapper).expand();
        }

        protected void setData (@Null StatData statData) {
            if (statData == null) {
                return;
            }
            if (statData.getType() == StatType.NUMBER) {
                label.setText(statData.getStat() + ":");
                valueLabel.setText(statData.getValue() + "");
            } else {
                label.setText(statData.getStat() + ":");
                valueLabel.setText(statData.getValue() + "%");
            }
        }
    }
}
