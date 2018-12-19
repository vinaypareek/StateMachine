package com.statemachine.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.statemachine.state.State;
import com.statemachine.state.StateMachine;

/**
 * Helper class to find out reachability of states
 * 
 * @author Vinay
 * 
 */
public class DepthFristSearch {
	private Map<String, Boolean> visited;

	/**
	 * Util function, implementation DFS algorithm
	 * 
	 * @param state
	 */
	private void dfsUtil(State state) {
		visited.put(state.getStateName(), Boolean.TRUE);

		Map<String, State> tranistion = state.getStateTransition();
		for (State transitionState : tranistion.values()) {
			if (!visited.containsKey(transitionState.getStateName()))
				dfsUtil(transitionState);
		}
	}

	/**
	 * Find out terminal state that are not reachable from initial states
	 * 
	 * @param context
	 * @return
	 */
	public List<String> dfsInitialState(StateMachine context) {
		visited = new HashMap<>();
		// do DFS from all initial state
		for (State state : context.getInitialStates()) {
			dfsUtil(state);
		}

		// check does any terminal state is not reachable
		List<String> states = new ArrayList<>();
		// boolean unreachableStates = false;
		for (State terminalState : context.getTerminalStates()) {
			if (!visited.containsKey(terminalState.getStateName())) {
				states.add(terminalState.getStateName());
			}
		}
		if (states.isEmpty()) {
			System.out.println("All terminal states are reachable\n");
		} else {
			System.out.println("Unreachable terminal states are " + states);
		}
		return states;
	}

	/**
	 * Find out intermediate states that does not lead to terminal state
	 * 
	 * @param context
	 * @return
	 */
	public List<String> dfsIntermediateState(StateMachine context) {
		List<String> states = new ArrayList<>();
		// do DFS from all intermediate state
		for (State intermediateState : context.getIntermediateStates()) {
			visited = new HashMap<>();
			dfsUtil(intermediateState);
			// check does terminal state is reachable from this intermediate
			// state or not
			boolean reachedTerminalState = false;
			for (State terminalState : context.getTerminalStates()) {
				if (visited.containsKey(terminalState.getStateName())) {
					reachedTerminalState = true;
					break;
				}
			}
			if (!reachedTerminalState) {
				states.add(intermediateState.getStateName());
			}
		}
		if (states.isEmpty()) {
			System.out
					.println("All intermediate states can reach to terminal state\n");
		} else {
			System.out
					.println("•	Intermediate states which cannot lead to a terminal state "
							+ states);
		}
		return states;
	}
}
