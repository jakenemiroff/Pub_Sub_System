package subscribers;

import events.AbstractEvent;
import pubSubServer.SubscriptionManager;
import states.subscriber.StateFactory;
import states.subscriber.StateName;

/**
 * @author kkontog, ktsiouni, mgrigori an example concrete subscriber
 */
class ConcreteSubscriberA extends AbstractSubscriber {

	protected ConcreteSubscriberA(StateName stateName) {
		System.out.println("\n Subscriber " + this.hashCode() + " created");
		setState(stateName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see subscribers.ISubscriber#setState(states.subscriber.StateName)
	 */
	public void setState(StateName stateName) {
		state = StateFactory.createState(stateName);
		System.out.println("\n Subscriber " + this.hashCode() + " has state " + stateName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see subscribers.ISubscriber#alert(events.AbstractEvent, java.lang.String)
	 */
	@Override
	public void alert(AbstractEvent event, String channelName) {
		System.out.println(
				"\n Subscriber " + this.hashCode() + " notified of event ::" + event.hashCode() + ":: published on channel " + channelName);
		state.handleEvent(this.hashCode(), event, channelName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see subscribers.ISubscriber#subscribe(java.lang.String)
	 */
	@Override
	public void subscribe(String channelName) {
		SubscriptionManager.getInstance().subscribe(channelName, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see subscribers.ISubscriber#unsubscribe(java.lang.String)
	 */
	@Override
	public void unsubscribe(String channelName) {
		SubscriptionManager.getInstance().unSubscribe(channelName, this);
		// was subscribe don't know why
	}

}
