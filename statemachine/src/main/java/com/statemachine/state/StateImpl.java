package com.statemachine.state;

import java.util.HashMap;
import java.util.Map;

import com.statemachine.event.Event;
import com.statemachine.exception.InvalidInputException;
import com.statemachine.exception.InvalidTransitionException;

public class StateImpl implements State {

	final StateMachine context;
	private Map<String, State> mapping = new HashMap<>();
	private String stateName;

	public StateImpl(StateMachine context, String name) {
		this.context = context;
		this.stateName = name;
	}

	public Map<String, State> getStateTransition() {
		return mapping;
	}

	public String getStateName() {
		return stateName;
	}

	public void addStateTransition(Event event, State nextState) {
		mapping.put(event.getEventName(), nextState);
	}

	@Override
	public String transition(Object input) throws InvalidTransitionException,
			InvalidInputException {
		String result = null;
		if (input instanceof State) {
			result = ((State) input).getStateName();
		} else if (input instanceof Event) {
			Event event = (Event) input;
			String eventName = event.getEventName();
			if (mapping.containsKey(eventName)) {
				context.setCurrentState(mapping.get(eventName));
			} else {
				throw new InvalidTransitionException(
						"Tranistion is not valid, no mapping define for this event :: "
								+ event + " and state :: " + this);
			}
			result = context.getCurrentState().getStateName();
		} else {
			throw new InvalidInputException("Input is invalid");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stateName == null) ? 0 : stateName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateImpl other = (StateImpl) obj;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return stateName;
	}

}
