package automail;

import exceptions.ExcessiveDeliveryException;
import exceptions.ItemTooHeavyException;
import exceptions.MailAlreadyDeliveredException;
import strategies.Automail;
import strategies.IMailPool;
import strategies.MailPool;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * This class simulates the behaviour of AutoMail
 */
public class Simulation {

	private static final String configFile = "automail.properties";

    public static void main(String[] args) throws IOException{

    	Configuration.init(configFile, args);

		Building.init(Configuration.FLOORS);
		Clock.init(Configuration.LAST_DELIVERY_TIME);
		Report.init(Configuration.STATISTICS_ENABLED);

		Automail automail = new Automail(Configuration.OVERDRIVE_ENABLED, Configuration.ROBOTS_NUM);
		MailGenerator mailGenerator = new MailGenerator(Configuration.MAIL_TO_CREATE, Configuration.MAIL_MAX_WEIGHT, automail.getMailPool(), Configuration.SEEDMAP);

        /** Initiate all the mail */
        mailGenerator.generateAllMail();
        // PriorityMailItem priority;  // Not used in this version
        while(Report.numOfDelivered() != mailGenerator.MAIL_TO_CREATE) {
        	// System.out.printf("Delivered: %4d; Created: %4d%n", MAIL_DELIVERED.size(), mailGenerator.MAIL_TO_CREATE);
            mailGenerator.step();
            try {
                automail.step();
			} catch (ExcessiveDeliveryException|ItemTooHeavyException e) {
				e.printStackTrace();
				System.out.println("Simulation unable to complete.");
				System.exit(0);
			}
            Clock.Tick();
        }
        Report.printResults();
    }

}
