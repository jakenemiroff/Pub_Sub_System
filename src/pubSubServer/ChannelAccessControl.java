
package pubSubServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import subscribers.AbstractSubscriber;

/**
 * @author kkontog, ktsiouni, mgrigori MUST Implements the Singleton design
 *         pattern Class that acts as an access control module that allows for
 *         the blocking and unblocking of specific subscribers for specific
 *         channels
 * 
 */
public class ChannelAccessControl {

	private static ChannelAccessControl instance = null;
	Map<String, List<AbstractSubscriber>> blackList;

	protected static ChannelAccessControl getInstance() {
		if (instance == null)
			instance = new ChannelAccessControl();
		return instance;
	}

	private ChannelAccessControl() {
		blackList = new HashMap<>();
	}

	/**
	 * 
	 * blocks the provided subscriber from accessing the designated channel
	 * 
	 * @param subscriber
	 *            an instance of any implementation of {@link AbstractSubscriber}
	 * @param channelName
	 *            a String value representing a valid channel name
	 */
	protected void blockSubcriber(AbstractSubscriber subscriber, String channelName) {

		List<AbstractSubscriber> blockedSubscribers = blackList.getOrDefault(channelName,
				new ArrayList<AbstractSubscriber>());
		blockedSubscribers.add(subscriber);
		System.out.println("\n Subscriber " + subscriber.hashCode() + " is blocked on channel " + channelName);
		blackList.put(channelName, blockedSubscribers);
	}

	/**
	 * unblocks the provided subscriber from accessing the designated channel
	 * 
	 * @param subscriber
	 *            an instance of any implementation of {@link AbstractSubscriber}
	 * @param channelName
	 *            a String value representing a valid channel name
	 */
	protected void unBlockSubscriber(AbstractSubscriber subscriber, String channelName) {

		List<AbstractSubscriber> blockedSubscribers;
		if ((blockedSubscribers = blackList.get(channelName)) == null)
			return;
		System.out.println("\n Subscriber " + subscriber.hashCode() + " is unblocked on channel " + channelName);
		blockedSubscribers.remove(subscriber);
	}

	/**
	 * checks if the provided subscriber is blocked from accessing the specified
	 * channel
	 * 
	 * @param subscriber
	 *            an instance of any implementation of {@link AbstractSubscriber}
	 * @param channelName
	 *            a String value representing a valid channel name
	 * @return true if blocked false otherwise
	 */
	protected boolean checkIfBlocked(AbstractSubscriber subscriber, String channelName) {

		List<AbstractSubscriber> blockedSubscribers;
		if ((blockedSubscribers = blackList.get(channelName)) == null)
			return false;
		return (blockedSubscribers.contains(subscriber));
	}

}
