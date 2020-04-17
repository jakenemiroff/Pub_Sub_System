package strategies.publisher;

/**
 * @author kkontog, ktsiouni, mgrigori
 * creates new {@link AbstractStrategy } objects
 * contributes to the Strategy design pattern
 * implements the FactoryMethod design pattern   
 */
public class StrategyFactory {

	
	/**
	 * creates a new {@link AbstractStrategy} using an entry from the {@link StrategyName} enumeration
	 * @param strategyName a value from the {@link StrategyName} enumeration specifying the strategy to be created 
	 * @return the newly created {@link AbstractStrategy} instance 
	 */
	public static AbstractStrategy createStrategy(StrategyName strategyName) {
		AbstractStrategy strategy;
		switch(strategyName) {
			case AStrategy:
				strategy = new AStrategy(strategyName);
				return strategy;
			case BStrategy:
				strategy = new BStrategy(strategyName);
				return strategy;
			case CStrategy:
				strategy = new CStrategy(strategyName);
				return strategy;
			case DStrategy:
				strategy = new DStrategy(strategyName);
				return strategy;
			case EStrategy:
				strategy = new EStrategy(strategyName);
				return strategy;
			default:
				strategy = new DefaultStrategy(strategyName);
				return strategy;
		}
	}
	
	
}
