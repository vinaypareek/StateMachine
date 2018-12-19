package com.statemachine.factory;

import com.statemachine.event.Event;
import com.statemachine.exception.DulicateEventException;
import com.statemachine.state.StateMachine;

/**
 * Factory class for event creation
 * 
 * @author Vinay
 * 
 */
public class EventFactory {

	public static Event createEventObject(StateMachine context, String name)
			throws DulicateEventException {
		Event event = new Event(name);
		if (context.getEvents().contains(event)) {
			throw new DulicateEventException("Event alredy exists :: " + name);
		}
		return event;
	}
}
