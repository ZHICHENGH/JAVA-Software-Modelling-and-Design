package strategies;

import automail.*;
import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
/**class aims to initialize the mailpool and robot*/
public class Automail {
	      
    private IRobot[] robots;
    private IMailPool mailPool;
    
    public Automail(boolean overdriveEnabled, int numRobots) {
    	// Swap between simple provided strategies and your strategies here
    	    	
    	/** Initialize the MailPool */
    	
    	this.mailPool = new MailPool();
    	
    	/** Initialize robots */
    	robots = new Robot[numRobots];
        for (int i = 0; i < numRobots; i++) {
            IRobotBehavior behavior = overdriveEnabled? new OverdriveEnabledBehavior(): new BaseBehavior();
            robots[i] = new Robot(behavior, mailPool);
        }

    }

    public IRobot[] getRobots() {
        return robots;
    }

    public IMailPool getMailPool() {
        return mailPool;
    }

    public void step() throws ExcessiveDeliveryException, ItemTooHeavyException {
        for (int i = 0; i < robots.length; i++) ((Robot)robots[i]).step();
    }
}
