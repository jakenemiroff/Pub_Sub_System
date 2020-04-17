package publishers;

import events.AbstractEvent;
import strategies.publisher.AbstractStrategy;
import strategies.publisher.StrategyFactory;

/**
 * @author kkontog, ktsiouni, mgrigori
 * 
 *         the WeatherPublisher class is an example of a ConcretePublisher
 *         implementing the IPublisher interface. Of course the publish methods
 *         could have far more interesting logics
 */
public class ConcretePublisher extends AbstractPublisher {

	/**
	 * @param concreteStrategy attaches a concreteStrategy generated from the
	 *                         {@link StrategyFactory#createStrategy(strategies.publisher.StrategyName)}
	 *                         method
	 */
	protected ConcretePublisher(AbstractStrategy concreteStrategy) {
		System.out.println("\n Publisher " + this.hashCode() + " created");
		setStrategy(concreteStrategy);
	}

	protected void setStrategy(AbstractStrategy strategy) {
		this.publishingStrategy = strategy;
		System.out.println("\n Publisher " + this.hashCode() + " has strategy " + strategy.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see publishers.IPublisher#publish(events.AbstractEvent)
	 */
	@Override
	public void publish(AbstractEvent event) {
		publishingStrategy.doPublish(event, this.hashCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see publishers.IPublisher#publish()
	 */
	@Override
	public void publish() {
		publishingStrategy.doPublish(this.hashCode());
	}

}
