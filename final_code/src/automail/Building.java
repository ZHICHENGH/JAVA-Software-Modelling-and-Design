package automail;
/**a class contains the information of the building
 */
public class Building {
	
	
    /** The number of floors in the building **/
    public static int FLOORS;
    
    /** Represents the ground floor location */
    public static final int LOWEST_FLOOR = 1;
    
    /** Represents the mailroom location */
    public static final int MAILROOM_LOCATION = 1;

    public static void init(int floor) {
        FLOORS = floor;
    }

}
