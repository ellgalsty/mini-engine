package com.tonir.games.managers.event;

public interface EventFilter<T extends Event> {

	boolean shouldExecute (T event);

}
