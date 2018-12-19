package com.statemachine;

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
 * Test Program
 * 
 */
public class App {
	public static void main(String[] args) throws DulicateStateException,
			DulicateEventException, InvalidStateException,
			InvalidEventException, InvalidTransitionException,
			InvalidInputException {
		System.out.println("App Test");
		// Context object
		StateMachine context = new StateMachineImpl();

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

		// validate states
		Validation.checkUnreachableTerminalStates(context);
		Validation.checkIntermediateStates(context);
		
		// fake terminal state
		State fakeTerminalState = StateFactory.createStateObject(context,
				"fakeTerminal");
		context.addTerminalState(fakeTerminalState);
		Validation.checkUnreachableTerminalStates(context);
		
		// add fake intermediate state
		State fakeIntermediateState = StateFactory.createStateObject(context,
				"fakeIntermediate");
		context.addIntermediateState(fakeIntermediateState);
		Validation.checkIntermediateStates(context);
		
		// time to interaction
		context.setCurrentState(initialState0S);
		System.out.println(context.transition(initialState0S));
		System.out.println(context.transition(event1R));
		System.out.println(context.transition(event2R));
		System.out.println(context.transition(event1R));
		System.out.println(context.transition(eventBuy));

		// time to interaction
		System.out.println("\n\nNew test");
		context.setCurrentState(initialState0S);
		System.out.println(context.transition(initialState0S));
		System.out.println(context.transition(event2R));
		System.out.println(context.transition(event2R));
		System.out.println(context.transition(eventBuy));

		// time to interaction
		System.out.println("\n\nNew test");
		context.setCurrentState(initialState0S);
		System.out.println(context.transition(initialState0S));
		System.out.println(context.transition(event2R));
		System.out.println(context.transition(eventCancel));

		// time to interaction
		System.out.println("\n\nNew test");
		context.setCurrentState(initialState0S);
		System.out.println(context.transition(initialState0S));
		System.out.println(context.transition(event1R));
		System.out.println(context.transition(eventCancel));

		// time to interaction
		System.out.println("\n\nNew test");
		context.setCurrentState(initialState0S);
		System.out.println(context.transition(initialState0S));
		try {
			System.out.println(context.transition(EventFactory
					.createEventObject(context, "newevent")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// time to interaction
		System.out.println("\n\nNew test");
		context.setCurrentState(initialState0S);
		System.out.println(context.transition(initialState0S));
		try {
			System.out.println(context.transition(StateFactory
					.createStateObject(context, "newstate")));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// time to interaction
		System.out.println("\n\nNew test");
		context.setCurrentState(initialState0S);
		System.out.println(context.transition(initialState0S));
		System.out.println(context.transition(event2R));
		System.out.println(context.transition(event2R));
		System.out.println(context.transition(eventBuy));
		System.out.println(context.transition(eventBuy));
	}
}
