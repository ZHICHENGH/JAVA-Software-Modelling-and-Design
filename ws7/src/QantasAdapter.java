import com.unimelb.swen30006.Qantas.QantasFlight;
import com.unimelb.swen30006.Qantas.QantasSearch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QantasAdapter implements SearchAdapter {
    private QantasSearch search;
    public QantasAdapter() {
        search = new QantasSearch();
    }

    @Override
    public SearchResult search(String departure, String destination, LocalDateTime departTime, int numPassengers) {
        ArrayList<QantasFlight> route = new ArrayList<>();
        route.add(new QantasFlight(departure,destination, departTime));
        List<QantasFlight> flights = search.search(route, numPassengers);
        SearchResult result = new SearchResult(flights.size());
        for (QantasFlight qantasFlight: flights) {
            Flight flight = new Flight(
                    qantasFlight.from(),
                    qantasFlight.to(),
                    qantasFlight.fare(),
                    qantasFlight.departAt(),
                    qantasFlight.arriveAt());
            result.addFlight(flight);
        }
        return result;
    }
}
