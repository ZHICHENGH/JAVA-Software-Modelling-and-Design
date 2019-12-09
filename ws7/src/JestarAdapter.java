import com.unimelb.swen30006.Jetstar.JetstarFlight;
import com.unimelb.swen30006.Jetstar.JetstarSearch;

import java.time.LocalDateTime;
import java.util.List;

public class JestarAdapter implements SearchAdapter {
    private JetstarSearch search;

    public JestarAdapter() {
        search = new JetstarSearch();
    }

    @Override
    public SearchResult search(String departure, String destination, LocalDateTime departTime, int numPassengers) {
        try {
            search.setNumPassengers(numPassengers);
            search.startInputFlights();
            search.inputFlight(departure, destination, departTime);
            search.endInputFlights();
            int num = search.search();
            List<JetstarFlight> flights = search.getSearchResult(0);
            SearchResult result = new SearchResult(num);
            for (JetstarFlight jetstarFlight: flights) {
                Flight flight = new Flight(
                        jetstarFlight.from(),
                        jetstarFlight.to(),
                        jetstarFlight.fare(),
                        jetstarFlight.departAt(),
                        jetstarFlight.arriveAt());
                result.addFlight(flight);
            }
            search.clearSearchResults();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SearchResult(0);
    }
}
