import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MiniExpedia {

    public static void main(String[] args) throws ParseException {

        Scanner in = new Scanner(System.in);
        boolean stopInput = false;
        String[] airlines = {"Jetstar","Qantas","Virgin"};
        List<String> departures = new ArrayList<>();
        List<String> destinations = new ArrayList<>();
        List<LocalDateTime> dates = new ArrayList<>();
        int i = 0;


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd&HH:mm");
        System.out.print("Number of Passengers: ");
        int numPassengers = in.nextInt();

        while(!stopInput) {
            System.out.print("Date & Time (ex. 2019-04-29&10:00): ");
            String dateStr = in.next();

            LocalDateTime date = null;
            //Check date
            try {
                date = LocalDateTime.parse(dateStr,formatter);
            }catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Wrong Date & Time Format.");
                continue;
            }
            dates.add(date);

            System.out.print("Departure airport: ");
            String departure = in.next();
            departures.add(departure);
            System.out.print("Destination airport: ");
            String destination = in.next();
            destinations.add(destination);


            System.out.print("More flight? (y/n): ");
            String input = in.next();
            input = input.toLowerCase();
            stopInput = input.equals("n");
            System.out.println(stopInput);

            i ++;
        }

        System.out.print("Preferred airline? (0: All, 1: Jetstar, 2: Qantas, 3: Virgin)");
        int airlineIndex = in.nextInt();
        in.close();

        SearchResult result = new SearchResult(0);
        SearchAdapter searchAdapter = AdapterFactory.instance().getAdapter(airlineIndex);
        while (i-->0) {
            result.concat(searchAdapter.search(departures.get(i),destinations.get(i),dates.get(i),numPassengers));;
        }

        for (Flight flight : result.getFlights()) {
            System.out.println(flight.departure + " " + flight.destination + " " + flight.airfare + " " + flight.departureTime + " " + flight.arrivalTime);
        }

    }



}
