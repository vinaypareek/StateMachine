package com.statemachine;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.statemachine.event.Event;
import com.statemachine.exception.DulicateEventException;
import com.statemachine.exception.DulicateStateException;
import com.statemachine.exception.InvalidEventException;
import com.statemachine.exception.InvalidInputException;
import com.statemachine.exception.InvalidStateException;
import com.statemachine.exception.InvalidTransitionException;
import com.statemachine.factory.EventFactory;
import com.statemachine.factory.StateFactory;
import com.statemachine.state.State;
import com.statemachine.state.StateMachine;
import com.statemachine.state.StateMachineImpl;
import com.statemachine.util.Constant;
import com.statemachine.util.Validation;

/**
 * Test case for state machine
 * 
 * @author Vinay
 * 
 */
public class StateMachineTestCase {

	private StateMachine context;

	@Before
	public void initStateMachine() throws DulicateStateException,
			DulicateEventException, InvalidStateException,
			InvalidEventException {
		// Context object
		context = new StateMachineImpl();

		// start state '*' this is to indicate event transition on all state
		State starState = StateFactory.createStateObject(context,
				Constant.STAR_STATE);
		context.addStarState(starState);

		// initial states
		State initialState0S = StateFactory.createStateObject(context, "0S");
		context.addInitialState(initialState0S);

		// terminal states
		State termimalStateCancelled = StateFactory.createStateObject(context,
				"CANCELLED");
		context.addTerminalState(termimalStateCancelled);
		State termimalStateCompleted = StateFactory.createStateObject(context,
				"COMPLETED");
		context.addTerminalState(termimalStateCompleted);

		// intermediate states
		State intermidiateState1S = StateFactory.createStateObject(context,
				"1S");
		context.addIntermediateState(intermidiateState1S);
		State intermidiateState2S = StateFactory.createStateObject(context,
				"2S");
		context.addIntermediateState(intermidiateState2S);
		State intermidiateState3S = StateFactory.createStateObject(context,
				"3S");
		context.addIntermediateState(intermidiateState3S);
		State intermidiateState4S = StateFactory.createStateObject(context,
				"4S");
		context.addIntermediateState(intermidiateState4S);

		// event
		Event event1R = EventFactory.createEventObject(context, "1R");
		context.addEvent(event1R);
		Event event2R = EventFactory.createEventObject(context, "2R");
		context.addEvent(event2R);
		Event eventCancel = EventFactory.createEventObject(context, "CANCEL");
		context.addEvent(eventCancel);
		Event eventBuy = EventFactory.createEventObject(context, "BUY");
		context.addEvent(eventBuy);

		// state transitions
		context.addStateTransition(initialState0S, event1R, intermidiateState1S);
		context.addStateTransition(initialState0S, event2R, intermidiateState2S);
		context.addStateTransition(intermidiateState1S, event1R,
				intermidiateState2S);
		context.addStateTransition(intermidiateState1S, event2R,
				intermidiateState3S);
		context.addStateTransition(intermidiateState2S, event1R,
				intermidiateState3S);
		context.addStateTransition(intermidiateState2S, event2R,
				intermidiateState4S);
		context.addStateTransition(intermidiateState3S, event1R,
				intermidiateState4S);
		context.addStateTransition(starState, eventCancel,
				termimalStateCancelled);
		context.addStateTransition(intermidiateState4S, eventBuy,
				termimalStateCompleted);
	}

	@Test(expected = InvalidStateException.class)
	public void testInvalidSourceState() throws DulicateStateException,
			InvalidStateException, InvalidEventException {
		// adding an unknown state
		State unknownState = StateFactory.createStateObject(context,
				"unknwonState");
		context.addStateTransition(unknownState, getEvent("1R"), getState("1S"));
	}

	@Test(expected = InvalidStateException.class)
	public void testInvalidDestinationState() throws DulicateStateException,
			InvalidStateException, InvalidEventException {
		// adding an unknown state
		State unknownState = StateFactory.createStateObject(context,
				"unknwonState");
		context.addStateTransition(getState("1S"), getEvent("1R"), unknownState);
	}

	@Test(expected = InvalidStateException.class)
	public void testCurrentStateNull() throws InvalidStateException,
			InvalidTransitionException, InvalidInputException,
			InvalidEventException {
		context.transition(getState("0S"));
	}

	@Test(expected = DulicateStateException.class)
	public void testDuplicateState() throws DulicateStateException {
		// creating a already exiting state
		StateFactory.createStateObject(context, "2S");
	}

	@Test(expected = DulicateEventException.class)
	public void testDuplicateEvent() throws DulicateEventException {
		// creating a already exiting state
		EventFactory.createEventObject(context, "1R");
	}

	@Test
	public void testCheckUnreachableTerminalStates() {
		List<String> unreachableStates = Validation
				.checkUnreachableTerminalStates(context);
		assertTrue(unreachableStates.isEmpty());
	}

	@Test
	public void testCheckIntermediateState() {
		List<String> intermediateState = Validation
				.checkIntermediateStates(context);
		assertTrue(intermediateState.isEmpty());
	}

	@Test
	public void testCheckUnreachableTerminalStatesWithFake()
			throws DulicateStateException {
		// fake terminal state
		State fakeTerminalState = StateFactory.createStateObject(context,
				"fakeTerminal");
		context.addTerminalState(fakeTerminalState);
		List<String> unreachableStates = Validation
				.checkUnreachableTerminalStates(context);
		assertTrue(!unreachableStates.isEmpty());
		assertTrue(unreachableStates.contains("fakeTerminal"));
	}

	@Test
	public void testCheckIntermediateStateWithFake()
			throws DulicateStateException {
		// add fake intermediate state
		State fakeIntermediateState = StateFactory.createStateObject(context,
				"fakeIntermediate");
		context.addIntermediateState(fakeIntermediateState);
		List<String> intermediateState = Validation
				.checkIntermediateStates(context);
		assertTrue(!intermediateState.isEmpty());
		assertTrue(intermediateState.contains("fakeIntermediate"));
	}

	@Test
	public void testBuyInput() throws InvalidStateException,
			InvalidTransitionException, InvalidInputException,
			InvalidEventException {
		// time to interaction
		context.setCurrentState(getState("0S"));
		assertEquals(context.transition(getState("0S")), "0S");
		assertEquals(context.transition(getEvent("1R")), "1S");
		assertEquals(context.transition(getEvent("2R")), "3S");
		assertEquals(context.transition(getEvent("1R")), "4S");
		assertEquals(context.transition(getEvent("BUY")), "COMPLETED");
	}

	@Test
	public void testCancelInput() throws InvalidStateException,
			InvalidTransitionException, InvalidInputException,
			InvalidEventException {
		context.setCurrentState(getState("0S"));
		assertEquals(context.transition(getState("0S")), "0S");
		assertEquals(context.transition(getEvent("2R")), "2S");
		assertEquals(context.transition(getEvent("CANCEL")), "CANCELLED");
	}

	@Test(expected = InvalidEventException.class)
	public void testInvalidEventInput() throws InvalidStateException,
			InvalidTransitionException, InvalidInputException,
			InvalidEventException, DulicateEventException {
		context.setCurrentState(getState("0S"));
		assertEquals(context.transition(getState("0S")), "0S");
		context.transition(EventFactory.createEventObject(context, "newevent"));
	}

	@Test(expected = InvalidStateException.class)
	public void testInvalidStateInput() throws InvalidStateException,
			InvalidTransitionException, InvalidInputException,
			DulicateEventException, DulicateStateException,
			InvalidEventException {
		context.setCurrentState(getState("0S"));
		assertEquals(context.transition(getState("0S")), "0S");
		context.transition(StateFactory.createStateObject(context, "newstate"));
	}

	@Test(expected = InvalidTransitionException.class)
	public void testInputAfterTerminateState() throws InvalidStateException,
			InvalidTransitionException, InvalidInputException,
			InvalidEventException, DulicateEventException {
		context.setCurrentState(getState("0S"));
		assertEquals(context.transition(getState("0S")), "0S");
		assertEquals(context.transition(getEvent("2R")), "2S");
		assertEquals(context.transition(getEvent("2R")), "4S");
		assertEquals(context.transition(getEvent("BUY")), "COMPLETED");
		context.transition(getEvent("BUY"));
	}

	@Test(expected = InvalidTransitionException.class)
	public void testInvalidTransitio() throws InvalidStateException,
			InvalidTransitionException, InvalidInputException,
			InvalidEventException, DulicateEventException {
		context.setCurrentState(getState("0S"));
		assertEquals(context.transition(getState("0S")), "0S");
		assertEquals(context.transition(getEvent("2R")), "2S");
		assertEquals(context.transition(getEvent("1R")), "3S");
		assertEquals(context.transition(getEvent("CANCEL")), "CANCELLED");
		context.transition(getEvent("BUY"));
	}

	private Event getEvent(String name) {
		for (Event event : context.getEvents()) {
			if (name.equals(event.getEventName())) {
				return event;
			}
		}
		return null;
	}

	private State getState(String name) {
		for (State state : context.getStates()) {
			if (name.equals(state.getStateName())) {
				return state;
			}
		}
		return null;
	}
}
