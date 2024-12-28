package com.tonir.games.managers.event;

import com.badlogic.gdx.utils.Pool;
import lombok.Getter;
import lombok.Setter;

public abstract class Event implements Pool.Poolable {

	@Setter @Getter
	private EventContext context = EventContext.MAIN;

	@Override
	public void reset () {

	}
}
