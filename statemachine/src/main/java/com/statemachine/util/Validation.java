package com.statemachine.util;

import java.util.List;

import com.statemachine.event.Event;
import com.statemachine.exception.InvalidEventException;
import com.statemachine.exception.InvalidStateException;
import com.statemachine.state.State;
import com.statemachine.state.StateMachine;

/**
 * Helper class to validating state and event
 * 
 * @author Vinay
 * 
 */
public class Validation {

	/**
	 * Validate state
	 * 
	 * @param stateList
	 * @param state
	 * @throws InvalidStateException
	 */
	public static void validateState(List<State> stateList, State state)
			throws InvalidStateException {
		if (!stateList.contains(state)) {
			throw new InvalidStateException("Invalid state :: " + state);
		}
	}

	/**
	 * Validate event
	 * 
	 * @param eventList
	 * @param event
	 * @throws InvalidEventException
	 */
	public static void validateEvent(List<Event> eventList, Event event)
			throws InvalidEventException {
		if (!eventList.contains(event)) {
			throw new InvalidEventException("Invalid event :: " + event);
		}
	}

	/**
	 * Find unreachable terminal states
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> checkUnreachableTerminalStates(
			StateMachine context) {
		DepthFristSearch dfs = new DepthFristSearch();
		return dfs.dfsInitialState(context);
	}

	/**
	 * Find intermediate state that don't sink to terminal state
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> checkIntermediateStates(StateMachine context) {
		DepthFristSearch dfs = new DepthFristSearch();
		return dfs.dfsIntermediateState(context);
	}
}
