package com.statemachine.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.statemachine.event.Event;
import com.statemachine.exception.InvalidEventException;
import com.statemachine.exception.InvalidInputException;
import com.statemachine.exception.InvalidStateException;
import com.statemachine.exception.InvalidTransitionException;
import com.statemachine.util.Constant;
import com.statemachine.util.Validation;

public class StateMachineImpl implements StateMachine {
	private List<State> initialStates = new ArrayList<>();
	private List<State> terminalStates = new ArrayList<>();
	private List<State> interMediateStates = new ArrayList<>();
	private List<State> allStates = new ArrayList<>();
	private List<Event> events = new ArrayList<>();
	private Map<String, List<State>> mapForAllStates = new HashMap<>();
	private State currentState;

	@Override
	public void addInitialState(State initialState) {
		initialStates.add(initialState);
		allStates.add(initialState);
		mapForAllStates
				.put(initialState.getStateName(), new ArrayList<State>());
	}

	@Override
	public void addTerminalState(State terminalState) {
		terminalStates.add(terminalState);
		allStates.add(terminalState);
		mapForAllStates.put(terminalState.getStateName(),
				new ArrayList<State>());
	}

	@Override
	public void addIntermediateState(State state) {
		interMediateStates.add(state);
		allStates.add(state);
		mapForAllStates.put(state.getStateName(), new ArrayList<State>());
	}

	@Override
	public void addStarState(State state) {
		allStates.add(state);
	}

	@Override
	public void addStateTransition(State startState, Event event,
			State nextState) throws InvalidStateException,
			InvalidEventException {
		// validate
		Validation.validateState(allStates, startState);
		Validation.validateState(allStates, nextState);
		Validation.validateEvent(events, event);
		// update mapping
		final String startStateName = startState.getStateName();
		List<State> adjacentStateList = null;
		if (mapForAllStates.containsKey(startStateName)) {
			adjacentStateList = mapForAllStates.get(startStateName);
			adjacentStateList.add(nextState);
		} else {
			adjacentStateList = new ArrayList<>();
			adjacentStateList.add(nextState);
		}
		mapForAllStates.put(startStateName, adjacentStateList);

		startState.addStateTransition(event, nextState);
		// if state is star state
		if (Constant.STAR_STATE.equalsIgnoreCase(startStateName)) {
			// we have to add this transition for all of states expect terminal
			// states
			// Add for initial states
			for (State state : initialStates) {
				state.addStateTransition(event, nextState);
			}
			// Add for intermediate states
			for (State state : interMediateStates) {
				state.addStateTransition(event, nextState);
			}
		}
	}

	@Override
	public State getCurrentState() {
		return currentState;
	}

	@Override
	public void setCurrentState(State state) {
		currentState = state;
	}

	@Override
	public String transition(Object input) throws InvalidStateException,
			InvalidTransitionException, InvalidInputException,
			InvalidEventException {
		// current state should not be null
		if (currentState == null) {
			throw new InvalidStateException("Current state is null");
		}

		// if we reached to terminal state, we are done :)
		if (isTerminalStateReached()) {
			throw new InvalidTransitionException(
					"Already reached to terminal state");
		}

		// validate input is valid for state/event
		if (input instanceof State) {
			State state = (State) input;
			if (!allStates.contains(state)) {
				throw new InvalidStateException(
						"State is not part of state machine");
			}
		} else if (input instanceof Event) {
			Event event = (Event) input;
			if (!events.contains(event)) {
				throw new InvalidEventException(
						"Event is not part of state machine");
			}
		}

		// do state transition
		return currentState.transition(input);
	}

	@Override
	public void addEvent(Event event) {
		events.add(event);
	}

	@Override
	public List<Event> getEvents() {
		return events;
	}

	@Override
	public List<State> getStates() {
		return allStates;
	}

	private boolean isTerminalStateReached() {
		return terminalStates.contains(currentState);
	}

	@Override
	public List<State> getInitialStates() {
		return initialStates;
	}

	@Override
	public List<State> getTerminalStates() {
		return terminalStates;
	}

	@Override
	public List<State> getIntermediateStates() {
		return interMediateStates;
	}

}
