package com.bootcamp.demo.pages.containers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.bootcamp.demo.data.Stat;
import com.bootcamp.demo.data.StatData;
import com.bootcamp.demo.data.StatsData;
import com.bootcamp.demo.engine.widgets.WidgetsContainer;

public class StatsContainer extends WidgetsContainer<StatWidget> {
    private final ObjectMap<Stat, StatWidget> statWidgets = new ObjectMap<>();

    public StatsContainer (Array<Stat> stats) {
        super(3);
        padLeft(50).defaults().space(25).expand().left();

        for (Stat stat : stats) {
            final StatWidget statContainer = new StatWidget();
            add(statContainer);
            statWidgets.put(stat, statContainer);
        }
    }

    public void setData (StatsData statsData) {
        for (StatData statData : statsData.getStats().values()) {
            final StatWidget widget = statWidgets.get(statData.getStat());
            widget.setData(statData);
        }
        for (ObjectMap.Entry<Stat, StatWidget> statEntry : statWidgets) {
            if (statEntry.value.label.getText().isEmpty()) {
                statEntry.value.setEmpty(statEntry.key);
            }
        }
    }
}
