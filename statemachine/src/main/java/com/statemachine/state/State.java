package com.statemachine.state;

import java.util.Map;

import com.statemachine.event.Event;
import com.statemachine.exception.InvalidInputException;
import com.statemachine.exception.InvalidTransitionException;

/**
 * Represent state object
 * 
 * @author Vinay
 * 
 */
public interface State {
	/**
	 * Transition mapping for this state
	 * 
	 * @return
	 */
	Map<String, State> getStateTransition();

	/**
	 * Get state name
	 * 
	 * @return
	 */
	String getStateName();

	/**
	 * Add state transition
	 * 
	 * @param event
	 * @param nextState
	 */
	void addStateTransition(Event event, State nextState);

	/**
	 * Perforce transition based on event
	 * 
	 * @param input
	 * @return
	 * @throws InvalidTransitionException
	 * @throws InvalidInputException
	 */
	String transition(Object input) throws InvalidTransitionException,
			InvalidInputException;
}
