package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import strategies.IMailPool;
/**describe the base behavior of robot */
public class BaseBehavior implements IRobotBehavior {
    int destinationFloor;
    int deliveryCounter;

    @Override
    /**
     * do the return behaviour of robot, if the robot return with
     * mail in Tube, add it into mailpool
     * when it arrives, change the statment and print out 
     * @param robot the robot make the movemnt
     * @param mailPool the mailPool for the whole system
     */
    public void returnBack(IRobot robot, IMailPool mailPool) {
        /** If its current position is at the mailroom, then the robot should change state */
        if(robot.getCurrentFloor() == Building.MAILROOM_LOCATION){
            if (robot.getTube() != null) {
                MailItem mailItem = robot.removeFromTube();
                mailPool.addToPool(mailItem);
                System.out.printf("T: %3d > old addToPool [%s]%n", Clock.Time(), mailItem.toString());
            }
            /** Tell the sorter the robot is ready */
            robot.changeState(IRobot.RobotState.WAITING);
        } else {
            /** If the robot is not at the mailroom floor yet, then move towards it! */
            move(robot, Building.MAILROOM_LOCATION);
        }
    }

    @Override
    /**do the waiting behaviour of robot, get mail from mailpool when there are mails
     * @param robot the robot that make the movement
     * @param mailPool the class stores undelivery mails
     * @exception ItemTooHeavyException occurs when the item weight beyond limit
     */
    public void whileWaiting(IRobot robot, IMailPool mailPool) throws ItemTooHeavyException {
        /** If the StorageTube is ready and the Robot is waiting in the mailroom then start the delivery */
        if (!mailPool.isEmpty()) {
            robot.addToHand(mailPool.popPool()); // hand first as we want higher priority delivered first
            if (!mailPool.isEmpty()) {
                robot.addToTube(mailPool.popPool());
            }
            setRoute(robot);
            deliveryCounter = 0;
            robot.changeState(IRobot.RobotState.DELIVERING);
        }
    }

    @Override
    /**do the deliver behavior of robot,send mail to the destination
     * if the robot finish one delivery, change the state to return or
     * deliver the mailitem in tube
     * @param robot the robot that make the movement
     * @exception ExcessiveDeliveryException occurs when deliverycounter larger than 2
     * @exception ItemTooHeavyException occurs when the item weight beyond limit
     */
    public void deliver(IRobot robot) throws ExcessiveDeliveryException, ItemTooHeavyException {
        if(robot.getCurrentFloor() == destinationFloor){ // If already here drop off either way
            /** Delivery complete, report this to the simulator! */
            Report.normalDelivery.deliver(robot.removeFromHand());
            deliveryCounter++;
            if(deliveryCounter > 2){  // Implies a simulation bug
                throw new ExcessiveDeliveryException();
            }
            /** Check if want to return, i.e. if there is no item in the tube*/
            if (robot.isEmpty()) {
                robot.changeState(IRobot.RobotState.RETURNING);
            }
            else {
                /** If there is another item, set the robot's route to the location to deliver the item */
                robot.addToHand(robot.removeFromTube());
                setRoute(robot);
                robot.changeState(IRobot.RobotState.DELIVERING);
            }
        } else {
            /** The robot is not at the destination yet, move towards it! */
            move(robot, destinationFloor);
        }
    }
    /**set the destination for robot to delivery item
     * @param robot the robot make the movement
     */
    void setRoute(IRobot robot) {
        /** Set the destination floor */
        destinationFloor = robot.getHand().destinationFloor;
    }
    /**make step movement of the robot.
     */
    void move(IRobot robot, int destinationFloor) {
        if (robot.getCurrentFloor() < destinationFloor)
            robot.move(1);
        else
            robot.move(-1);
    }

}
