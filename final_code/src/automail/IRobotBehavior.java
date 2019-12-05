package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import strategies.IMailPool;
/**IRnotBehavior is the interface for basic robot delivery functions */
public interface IRobotBehavior {
    void deliver(IRobot robot) throws ExcessiveDeliveryException, ItemTooHeavyException;
    void returnBack(IRobot robot, IMailPool mailPool);
    void whileWaiting(IRobot robot, IMailPool mailPool) throws ItemTooHeavyException;

}
