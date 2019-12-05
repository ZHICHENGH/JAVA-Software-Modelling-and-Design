package automail;

import exceptions.ItemTooHeavyException;
/**IRobot is the interface for basic functions of robots*/
public interface IRobot {
    enum RobotState { DELIVERING, WAITING, RETURNING }
    void move(int floors);
    void addToHand(MailItem mailItem) throws ItemTooHeavyException;
    void addToTube(MailItem mailItem) throws ItemTooHeavyException;
    void changeState(RobotState nextState);
    MailItem removeFromHand();
    MailItem removeFromTube();
    boolean isEmpty();
    MailItem getTube();
    MailItem getHand();
    RobotState getState();
    int getCurrentFloor();
}
