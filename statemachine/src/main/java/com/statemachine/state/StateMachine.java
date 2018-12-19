package com.statemachine.state;

import java.util.List;

import com.statemachine.event.Event;
import com.statemachine.exception.InvalidEventException;
import com.statemachine.exception.InvalidInputException;
import com.statemachine.exception.InvalidStateException;
import com.statemachine.exception.InvalidTransitionException;

public interface StateMachine {
	/**
	 * Add initial states
	 * 
	 * @param initialState
	 */
	void addInitialState(State initialState);

	/**
	 * Add terminal states
	 * 
	 * @param terminalState
	 */
	void addTerminalState(State terminalState);

	/**
	 * Add intermediate states
	 * 
	 * @param state
	 */
	void addIntermediateState(State state);

	/**
	 * Add star state, star state means for all the state apart from terminal
	 * state
	 * 
	 * @param state
	 */
	void addStarState(State state);

	/**
	 * Add state transition
	 * 
	 * @param startState
	 * @param event
	 * @param newState
	 * @throws InvalidStateException
	 * @throws InvalidEventException
	 */
	void addStateTransition(State startState, Event event, State newState)
			throws InvalidStateException, InvalidEventException;

	/**
	 * Get Current state
	 * 
	 * @return
	 */
	State getCurrentState();

	/**
	 * Set current state
	 * 
	 * @param state
	 */
	void setCurrentState(State state);

	/**
	 * Perform transition based on event
	 * 
	 * @param input
	 * @return
	 * @throws InvalidStateException
	 * @throws InvalidTransitionException
	 * @throws InvalidInputException
	 * @throws InvalidEventException
	 */
	String transition(Object input) throws InvalidStateException,
			InvalidTransitionException, InvalidInputException,
			InvalidEventException;

	/**
	 * Add event
	 * 
	 * @param event
	 */
	void addEvent(Event event);

	/**
	 * Get event list
	 * 
	 * @return
	 */
	List<Event> getEvents();

	/**
	 * Get all state list
	 * 
	 * @return
	 */
	List<State> getStates();

	/**
	 * Get initial state list
	 * 
	 * @return
	 */
	List<State> getInitialStates();

	/**
	 * Get terminal state list
	 * 
	 * @return
	 */
	List<State> getTerminalStates();

	/**
	 * Get Intermediate state list
	 * 
	 * @return
	 */
	List<State> getIntermediateStates();

}
