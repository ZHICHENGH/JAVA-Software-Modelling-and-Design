package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import strategies.IMailPool;
import java.util.Map;
import java.util.TreeMap;

/**
 * The robot delivers mail!
 */
public class Robot implements IRobot{
	
    static public final int INDIVIDUAL_MAX_WEIGHT = 2000;

    protected final String id;
    /** Possible states the robot can be in */
    private IRobotBehavior behavior;
    private RobotState currentState;
    private int currentFloor;
    private IMailPool mailPool;
    
    private MailItem deliveryItem = null;
    private MailItem tube = null;
    

    /**
     * Initiates the robot's location at the start to be at the mailroom
     * also set it to be waiting for mail.
     * @param behavior governs selection of mail items for delivery and behaviour on priority arrivals
     * @param mailPool is the source of mail items
     */
    public Robot(IRobotBehavior behavior, IMailPool mailPool){
    	id = "R" + hashCode();
        // currentState = RobotState.WAITING;
    	currentState = RobotState.RETURNING;
        currentFloor = Building.MAILROOM_LOCATION;
        this.behavior = behavior;
        this.mailPool = mailPool;
    }

    /**
     * This is called on every time step
     * @throws ExcessiveDeliveryException if robot delivers more than the capacity of the tube without refilling
     */
    public void step() throws ExcessiveDeliveryException, ItemTooHeavyException {
    	switch(currentState) {
    		/** This state is triggered when the robot is returning to the mailroom after a delivery */
    		case RETURNING:
    			behavior.returnBack(this, mailPool);
    			break;

    		case WAITING:
                behavior.whileWaiting(this, mailPool);
                break;

            case DELIVERING:
                behavior.deliver(this);
                break;
        }
    }

	public MailItem getTube() {
		return tube;
	}

    @Override
    /**get the mailItem in the hand.
     * @return deliveryItem
     */
    public MailItem getHand() {
        return deliveryItem;
    }

    @Override
    /**get the state of the robot.
     * @return currentState
     */
    public RobotState getState() {
        return currentState;
    }

    @Override
    /**get the current floor of robot.
     * @return currentFloor
     */
    public int getCurrentFloor() {
        return currentFloor;
    }

    static private int count = 0;
	static private Map<Integer, Integer> hashMap = new TreeMap<Integer, Integer>();
    /**judge whether the robot has mail items */
	public boolean isEmpty() {
		return (deliveryItem == null && tube == null);
	}

    @Override
    /**make movement of the robot
     * @param floors the number of floors(can be negative) that the robot is going to move
     */
    public void move(int floors) {
	    int nextFloor = currentFloor + floors;
        if (nextFloor > Building.FLOORS) currentFloor = Building.FLOORS;
        else if(nextFloor < Building.LOWEST_FLOOR) currentFloor = Building.LOWEST_FLOOR;
        else currentFloor = nextFloor;
    }

    @Override
    /**add mailitem to hand
     * @param mailItem the Item that is going to add to hand
     * @exception ItemTooHeavyException occurs when the item weight is beyond the limit
     */
    public void addToHand(MailItem mailItem) throws ItemTooHeavyException {
		assert(deliveryItem == null);
		deliveryItem = mailItem;
		if (deliveryItem.weight > INDIVIDUAL_MAX_WEIGHT) throw new ItemTooHeavyException();
	}

    @Override
    /**add mailitem to Tube
     * @param mailItem the Item that is going to add to Tube
     * @exception ItemTooHeavyException occurs when the item weight is beyond the limit
     */
	public void addToTube(MailItem mailItem) throws ItemTooHeavyException {
		assert(tube == null);
		tube = mailItem;
		if (tube.weight > INDIVIDUAL_MAX_WEIGHT) throw new ItemTooHeavyException();
	}

    @Override
    /**remove item from hand 
     * @return the mailItem in the hand
    */
    public MailItem removeFromHand() {
        MailItem mailItem = deliveryItem;
        deliveryItem = null;
        return  mailItem;
    }

    @Override
    /**remove item from tube
     * @return the mailitem in the tube
     */
    public MailItem removeFromTube() {
        MailItem mailItem = tube;
        tube = null;
        return  mailItem;
    }

    @Override

    public int hashCode() {
        Integer hash0 = super.hashCode();
        Integer hash = hashMap.get(hash0);
        if (hash == null) { hash = count++; hashMap.put(hash0, hash); }
        return hash;
    }

    private String getIdTube() {
        return String.format("%s(%1d)", id, (tube == null ? 0 : 1));
    }

    /**
     * Prints out the change in state
     * @param nextState the state to which the robot is transitioning
     */
    @Override
    public void changeState(RobotState nextState){
        assert(!(deliveryItem == null && tube != null));
        if (currentState != nextState) {
            System.out.printf("T: %3d > %7s changed from %s to %s%n", Clock.Time(), getIdTube(), currentState, nextState);
        }
        currentState = nextState;
        if(nextState == RobotState.DELIVERING){
            System.out.printf("T: %3d > %7s-> [%s]%n", Clock.Time(), getIdTube(), deliveryItem.toString());
        }
    }

}
