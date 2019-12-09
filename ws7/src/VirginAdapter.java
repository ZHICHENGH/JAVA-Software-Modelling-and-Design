import com.unimelb.swen30006.Virgin.VirginFlight;
import com.unimelb.swen30006.Virgin.VirginSearch;

import java.time.LocalDateTime;
import java.util.List;

public class VirginAdapter implements SearchAdapter {
    private VirginSearch search;
    public VirginAdapter() {
        search = new VirginSearch();
    }

    @Override
    public SearchResult search(String departure, String destination, LocalDateTime departTime, int numPassengers) {
        List<VirginFlight> flights = search.search(departure, destination, departTime, numPassengers);
        SearchResult result = new SearchResult(flights.size());
        for (VirginFlight virginFlight: flights) {
            Flight flight = new Flight(
                    virginFlight.getDeparture(),
                    virginFlight.getDestination(),
                    virginFlight.getAirfare(),
                    virginFlight.getDepartureTime(),
                    virginFlight.getArrivalTime());
            result.addFlight(flight);
        }
        return result;
    }
}
