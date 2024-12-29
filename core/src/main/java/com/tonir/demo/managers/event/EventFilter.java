package com.tonir.demo.managers.event;

public interface EventFilter<T extends Event> {

	boolean shouldExecute (T event);

}
