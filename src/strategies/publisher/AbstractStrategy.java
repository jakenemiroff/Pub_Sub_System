package strategies.publisher;

import java.util.LinkedList;
import java.util.List;

import events.AbstractEvent;
import events.EventFactory;
import events.EventMessage;
import events.EventType;
import pubSubServer.AbstractChannel;
import pubSubServer.ChannelDiscovery;
import pubSubServer.ChannelEventDispatcher;
import publishers.AbstractPublisher;

/**
 * @author kkontog, ktsiouni, mgrigori Base Interface for the implementation of
 *         the Strategy Design Pattern
 */
public abstract class AbstractStrategy {

	private StrategyName strategyName;
	int mod;

	protected AbstractStrategy(StrategyName strategyName, int offset) {
		this.strategyName = strategyName;
		this.mod = offset;
	}

	/**
	 * Whenever a publisher's {@link AbstractPublisher#publish()} is called, the
	 * call is forwarded to the particular Publisher's IStrategy implementation of
	 * this method
	 * 
	 * @param publisherId the hashCode or any other unique identifier of the
	 *                    publisher of an AbstractEvent
	 */
	public void doPublish(int publisherId) {
		String header = "Default Event";
		String body = "This is a default event";

		doPublish(EventFactory.createEvent(EventType.Default, publisherId, new EventMessage(header, body)),
				publisherId);
	}

	/**
	 * 
	 * Whenever a publisher's {@link AbstractPublisher#publish(AbstractEvent)} is
	 * called, the call is forwarded to the particular Publisher's IStrategy
	 * implementation of this method
	 * 
	 * @param event       the event to be published
	 * @param publisherId the hashCode or any other unique identifier of the
	 *                    publisher of an AbstractEvent
	 */
	public void doPublish(AbstractEvent event, int publisherId) {
		System.out.println("\n Publisher " + publisherId + " publishes event " + event.hashCode());
		List<AbstractChannel> channel = ChannelDiscovery.getInstance().listChannels();
		List<String> channelPost = new LinkedList<String>();
		for (int i = mod; i < channel.size(); i = i + 6) {
			channelPost.add(channel.get(i).getChannelTopic());
		}
		ChannelEventDispatcher.getInstance().postEvent(publisherId,event, channelPost);
	}
	
	public StrategyName getName() {
		return strategyName;
	}

}
