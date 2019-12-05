package automail;
/**record the time during the program running */
public class Clock {
	
	/** Represents the current time **/
    private static int Time = 0;
    
    /** The threshold for the latest time for mail to arrive **/
    public static int LAST_DELIVERY_TIME;

    public static int Time() {
    	return Time;
    }
    
    public static void Tick() {
    	Time++;
    }

    public static void init(int lastDeliveryTime) {LAST_DELIVERY_TIME = lastDeliveryTime;}
}
