package states.subscriber;

import events.AbstractEvent;
import subscribers.AbstractSubscriber;

/**
 * @author kkontog, ktsiouni, mgrigori Base Interface for the implementation of
 *         the State design pattern
 */
public abstract class AbstractState {

	private StateName stateName;

	protected AbstractState(StateName stateName) {
		this.stateName = stateName;
	}

	/**
	 * the handleEvent method will behave appropriately depending on the
	 * implementation. In general it will do something, after an AbstractEvent is
	 * published on an AbstractChannel to which the ConcreteState's host Object is
	 * subscribed
	 * 
	 * @param event
	 * @param channelName
	 */
	public void handleEvent(int sub, AbstractEvent event, String channelName) {
		System.out.println("\n Subscriber " + sub + " in state " + stateName + " has handled the event ::" + event.hashCode()
				+ ":: published on channel " + channelName);
	}

	protected StateName getState() {
		return stateName;
	}

}
