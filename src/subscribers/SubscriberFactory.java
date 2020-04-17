package subscribers;

import java.util.HashMap;

import states.subscriber.StateName;

/**
 * @author kkontog, ktsiouni, mgrigori
 *  
 */
/**
 * @author kkontog, ktsiouni, mgrigori creates new {@link AbstractSubscriber}
 *         objects contributes to the State design pattern implements the
 *         FactoryMethod design pattern
 */
public class SubscriberFactory {

	private static HashMap<Integer, AbstractSubscriber> listOfSubscribers = new HashMap<Integer, AbstractSubscriber>();

	/**
	 * creates a new {@link AbstractSubscriber} using an entry from the
	 * {@link SubscriberType} enumeration
	 * 
	 * @param subscriberType a value from the {@link SubscriberType} enumeration
	 *                       specifying the type of Subscriber to be created
	 * @return the newly created {@link AbstractSubscriber} instance
	 */
	public static AbstractSubscriber createSubscriber(int subscriberType, StateName stateName) {
		if (listOfSubscribers.containsKey(subscriberType)) {
			AbstractSubscriber sub = listOfSubscribers.get(subscriberType);
			sub.setState(stateName);
			return sub;
		}
		listOfSubscribers.put(subscriberType, new ConcreteSubscriberA(stateName));
		return listOfSubscribers.get(subscriberType);
	}

}
