
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class SearchResult {
    private HashSet<Flight> flights;

    public SearchResult(int initCapacity) {
        flights = new HashSet<>(initCapacity);
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void concat(SearchResult result) {
        for (Flight flight: result.getFlights()) {
            addFlight(flight);
        }
    }

    public List<Flight> getFlights() {
        return flights.stream().sorted().collect(Collectors.toList());
    }

}
