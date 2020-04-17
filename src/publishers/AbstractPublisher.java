package publishers;

import events.AbstractEvent;
import strategies.publisher.AbstractStrategy;


/**
 * @author kkontog, ktsiouni, mgrigori base Interface implemented by all
 *         ConcretePublisher Classes
 */
public abstract class AbstractPublisher {

	protected AbstractStrategy publishingStrategy = null;
	/**
	 * all implementations of this Interface MUST contain an instance variable of 
	 * type {@link AbstractStrategy} which has the methods:
	 * 1) {@link AbstractStrategy#doPublish(int)}
	 * 2) {@link AbstractStrategy#doPublish(AbstractEvent, int)}
	 * which allows for the publishing of an {@link AbstractEvent} object
	 * 
	 * @param event an event which is to be published
	 * 
	 */
	protected void setStrategy(AbstractStrategy strategy) {};
	
	public void publish(AbstractEvent event) {};

	/**
	 * all implementations of this Interface MUST contain an instance variable of 
	 * type {@link AbstractStrategy} which has the methods:
	 * 1) {@link AbstractStrategy#doPublish(int)}
	 * 2) {@link AbstractStrategy#doPublish(AbstractEvent, int)}
	 * which allows for the publishing of an {@link AbstractEvent} object
	 * 
	 * @param event an event which is to be published
	 * 
	 */
	public void publish() {};

}
