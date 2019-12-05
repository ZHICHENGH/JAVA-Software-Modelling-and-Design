package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import strategies.IMailPool;
/**extends BaseBehavior, make behavior when robot is under overdriving */
public class OverdriveEnabledBehavior extends BaseBehavior {
    private static final int COOLDOWN_TIME = 5;

    private boolean overdrive;
    private int cooldown;
    @Override
    /**
     * do the return behaviour of robot
     * if it is under overdriving 
     * call the super returnback(normal returnback) to return when it finish cooldown
     * @param robot the robot make the movemnt
     * @param mailPool the mailPool for the whole system
     */
    public void returnBack(IRobot robot, IMailPool mailPool) {
        if(overdrive) {
            if(cooldown == 0) {
                stopOverdrive();
                super.returnBack(robot, mailPool);
                return;
            }
            else {
                cooldown();
                return;
            }
        }
        super.returnBack(robot, mailPool);
    }

    @Override
     /**do the waiting behaviour of robot, get mail from mailpool when there are mails
      * if get a HighPriorityMail, start overdriving.
     * @param robot the robot that make the movement
     * @param mailPool the class stores undelivery mails
     * @exception ItemTooHeavyException occurs when the item weight beyond limit
     */
    public void whileWaiting(IRobot robot, IMailPool mailPool) throws ItemTooHeavyException {
        if (!mailPool.isEmpty()) {
            robot.addToHand(mailPool.popPool());
            if(isHighPriorityMail(robot.getHand())) {
                startOverdrive();
            }
            else if (!mailPool.isEmpty()) {
                robot.addToTube(mailPool.popPool());
            }
            setRoute(robot);
            deliveryCounter = 0;

            robot.changeState(IRobot.RobotState.DELIVERING);
        }
    }

    @Override
    /**do the normal deliver or overdriving deliver of robot 
     * if the robot finish one delivery, change the state to return or
     * deliver the mailitem in tube
     * @param robot the robot that make the movement
     * @exception ExcessiveDeliveryException occurs when delivery counter larger than limit
     * @exception ItemTooHeavyException occurs when the item weight beyond limit
     */
    public void deliver(IRobot robot) throws ExcessiveDeliveryException, ItemTooHeavyException {
        if(robot.getCurrentFloor() == destinationFloor){ // If already here drop off either way
            /** Delivery complete, report this to the simulator! */
            if (overdrive)
                Report.overdriveDelivery.deliver(robot.removeFromHand());
            else
                Report.normalDelivery.deliver(robot.removeFromHand());
            deliveryCounter++;
            if(deliveryCounter > (overdrive? 1: 2)){  // Implies a simulation bug
                throw new ExcessiveDeliveryException();
            }
            /** Check if want to return, i.e. if there is no item in the tube*/
            if (robot.isEmpty()) {
                if(overdrive)
                    setCoolDown();
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

    @Override
     /**make step movement of the robot under overdrive and normal delivery
      */
    void move(IRobot robot, int destinationFloor) {
        int moveStep = overdrive? 2: 1;
        if (robot.getCurrentFloor() < destinationFloor){
            int delta = destinationFloor - robot.getCurrentFloor();
            robot.move(delta > moveStep ? moveStep: delta);
        }

        else {
            int delta = robot.getCurrentFloor() - destinationFloor;
            robot.move(delta > moveStep ? -moveStep: -delta);
        }
    }
     /**make cool down for overdriving robot
      */
    private void cooldown() {
        cooldown--;
    }
    /**set cool down time for over drive robot*/
    private void setCoolDown() {
        cooldown = COOLDOWN_TIME;
    }
    /**stop the over drive */
    private void stopOverdrive() {
        overdrive = false;
    }
    /**start the state of over drive */
    private void startOverdrive() {
        overdrive = true;
    }
    /**judge whether the mailItem is a high Priority MailItem 
     * @param mailItem the delivered item
    */
    private boolean isHighPriorityMail(MailItem mailItem) {
        return mailItem instanceof PriorityMailItem && ((PriorityMailItem) mailItem).getPriorityLevel() > 50;
    }
}
