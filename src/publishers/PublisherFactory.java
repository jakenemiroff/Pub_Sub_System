package publishers;

import java.util.HashMap;

import strategies.publisher.AbstractStrategy;
import strategies.publisher.StrategyFactory;
import strategies.publisher.StrategyName;

/**
 * @author kkontog, ktsiouni, mgrigori creates new {@link AbstractPublisher}
 *         objects contributes to the Strategy design pattern implements the
 *         FactoryMethod design pattern
 */
public class PublisherFactory {

	private static HashMap<Integer, AbstractPublisher> listOfPublishers = new HashMap<Integer, AbstractPublisher>();

	/**
	 * This is an implementation of the Factory Method design pattern Creates an
	 * instance of any of the classes implementing the top level Interface
	 * IPublisher
	 * 
	 * note we have multiple entries that return instances of the same
	 * ConcretePublisher class
	 * 
	 * @param publisherType an entry from the {@link PublisherType} enumeration
	 * @param strategyName  an entry from the {@link StrategyName} enumeration
	 * @return an instance of the specified IPublisher implementation with the
	 *         specified strategyName attached to it
	 */
	public static AbstractPublisher createPublisher(int publisherType, StrategyName strategyName) {
		AbstractStrategy strategy = StrategyFactory.createStrategy(strategyName);
		if (listOfPublishers.containsKey(publisherType)) {
			AbstractPublisher pub = listOfPublishers.get(publisherType);
			pub.setStrategy(strategy);
			return pub;
		}
		listOfPublishers.put(publisherType, new ConcretePublisher(strategy));
		return listOfPublishers.get(publisherType);
	}

}
