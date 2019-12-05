package automail;
import exceptions.MailAlreadyDeliveredException;

import java.util.*;
/**the class to make print */
public class Report {

    public static class ReportDelivery implements IMailDelivery {
        int totalPackage;
        int totalWeight;

        /** print the deliver result and make change of totalscore, totalpackage and totalweight */
        public void deliver(MailItem deliveryItem){
            if(!deliveredMail.contains(deliveryItem)){
                deliveredMail.add(deliveryItem);
                System.out.printf("T: %3d > Delivered(%4d) [%s]%n", Clock.Time(), deliveredMail.size(), deliveryItem.toString());
                // Calculate delivery score
                totalScore += calculateDeliveryScore(deliveryItem);

                totalPackage ++;
                totalWeight += deliveryItem.weight;
            }
            else{
                try {
                    throw new MailAlreadyDeliveredException();
                } catch (MailAlreadyDeliveredException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static class ReportOverdriveDelivery extends ReportDelivery {}


    public static ReportDelivery normalDelivery;
    public static ReportOverdriveDelivery overdriveDelivery;

    private static boolean statisticsEnabled;
    private static List<MailItem> deliveredMail;
    private static double totalScore;


    public static void init(boolean statisticsEnabled) {
        Report.statisticsEnabled = statisticsEnabled;
        deliveredMail = new LinkedList<>();
        totalScore = 0;
        normalDelivery = new ReportDelivery();
        overdriveDelivery = new ReportOverdriveDelivery();
    }
    /**get the num of MailItems delivered
    */
    public static int numOfDelivered() {
        return deliveredMail.size();
    }
    /**print out the results */
    public static void printResults(){
        System.out.println("T: "+Clock.Time()+" | Simulation complete!");
        System.out.println("Final Delivery time: "+Clock.Time());
        System.out.printf("Final Score: %.2f%n", totalScore);

        if(statisticsEnabled) {
            printStatistics();
        }
    }
    /**print out the statistics */
    public static void printStatistics() {
        System.out.println("Normal packages: " + normalDelivery.totalPackage);
        System.out.println("Normal total weight: " + normalDelivery.totalWeight);

        System.out.println("Overdrive packages: " + overdriveDelivery.totalPackage);
        System.out.println("Overdrive total weight: " + overdriveDelivery.totalWeight);
    }
    /**calculate the delivery score */
    private static double calculateDeliveryScore(MailItem deliveryItem) {
        // Penalty for longer delivery times
        final double penalty = 1.2;
        double priority_weight = 0;
        // Take (delivery time - arrivalTime)**penalty * (1+sqrt(priority_weight))
        if(deliveryItem instanceof PriorityMailItem){
            priority_weight = ((PriorityMailItem) deliveryItem).getPriorityLevel();
        }
        return Math.pow(Clock.Time() - deliveryItem.getArrivalTime(),penalty)*(1+Math.sqrt(priority_weight));
    }
}
