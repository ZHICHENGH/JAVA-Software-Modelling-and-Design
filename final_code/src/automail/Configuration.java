package automail;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * this class is used to read the property
 * @author tianqiu
 *
 */

public class Configuration {
    public static HashMap<Boolean, Integer> SEEDMAP;
    public static int FLOORS;
    public static int MAIL_TO_CREATE;
    public static int MAIL_MAX_WEIGHT;
    public static int LAST_DELIVERY_TIME;
    public static int ROBOTS_NUM;
    public static boolean OVERDRIVE_ENABLED;
    public static boolean STATISTICS_ENABLED;

    private static Properties  automailProperties;

    public static void init(String fileName, String[] args) throws IOException {
        //initialize the properties;
        automailProperties = new Properties();
        initProperties(automailProperties);
        readFile(fileName);
        setVariable(args);
    }

    private static void initProperties(Properties automailProperties) {
        automailProperties.setProperty("Robots", "Standard");
        automailProperties.setProperty("MailPool", "strategies.SimpleMailPool");
        automailProperties.setProperty("Floors", "10");
        automailProperties.setProperty("Fragile", "false");
        automailProperties.setProperty("Mail_to_Create", "80");
        automailProperties.setProperty("Last_Delivery_Time", "100");
        automailProperties.setProperty("Overdrive", "false");
        automailProperties.setProperty("Statistics", "false");
    }

    private static void readFile(String fileName) throws IOException {
        // Read properties
        FileReader inStream = null;
        try {
            inStream = new FileReader(fileName);
            automailProperties.load(inStream);
        } finally {
            if (inStream != null) {
                inStream.close();
            }
        }
    }


    private static void setVariable(String[] args) {

        // Floors
        FLOORS = Integer.parseInt(automailProperties.getProperty("Floors"));
        System.out.println("Floors: " + FLOORS);

        // Mail_to_Create
        MAIL_TO_CREATE = Integer.parseInt(automailProperties.getProperty("Mail_to_Create"));
        System.out.println("Mail_to_Create: " + MAIL_TO_CREATE);

        // Mail_to_Create
        MAIL_MAX_WEIGHT = Integer.parseInt(automailProperties.getProperty("Mail_Max_Weight"));
        System.out.println("Mail_Max_Weight: " + MAIL_MAX_WEIGHT);

        // Last_Delivery_Time
        LAST_DELIVERY_TIME = Integer.parseInt(automailProperties.getProperty("Last_Delivery_Time"));
        System.out.println("Last_Delivery_Time: " + LAST_DELIVERY_TIME);

        // Overdrive ability
        OVERDRIVE_ENABLED = Boolean.parseBoolean(automailProperties.getProperty("Overdrive"));
        System.out.println("Overdrive enabled: " + OVERDRIVE_ENABLED);

        // Statistics tracking
        STATISTICS_ENABLED = Boolean.parseBoolean(automailProperties.getProperty("Statistics"));
        System.out.println("Statistics enabled: " + STATISTICS_ENABLED);

        ROBOTS_NUM = Integer.parseInt(automailProperties.getProperty("Robots"));
        System.out.print("Robots: "); System.out.println(ROBOTS_NUM);
        assert(ROBOTS_NUM > 0);

        //Seed
        String seedProp = automailProperties.getProperty("Seed");
        SEEDMAP = new HashMap<>();
        if (args.length == 0) { // No arg
            if (seedProp == null) { // and no property
                SEEDMAP.put(false, 0); // so randomise
            } else { // Use property seed
                SEEDMAP.put(true, Integer.parseInt(seedProp));
            }
        } else { // Use arg seed - overrides property
            SEEDMAP.put(true, Integer.parseInt(args[0]));
        }
        Integer seed = SEEDMAP.get(true);
        System.out.println("Seed: " + (seed == null ? "null" : seed.toString()));
    }
}
