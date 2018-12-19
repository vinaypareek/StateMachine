package com.statemachine.factory;

import com.statemachine.exception.DulicateStateException;
import com.statemachine.state.State;
import com.statemachine.state.StateImpl;
import com.statemachine.state.StateMachine;

/**
 * Factory class for state creation
 * 
 * @author Vinay
 * 
 */
public class StateFactory {

	public static State createStateObject(StateMachine context, String name)
			throws DulicateStateException {
		StateImpl state = new StateImpl(context, name);
		if (context.getStates().contains(state)) {
			throw new DulicateStateException("State alredy exists :: " + name);
		}
		return state;
	}
}
