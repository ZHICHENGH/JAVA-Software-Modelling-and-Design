import java.time.LocalDateTime;

public class AllAdapter implements SearchAdapter {
    private SearchAdapter adapters[];

    public AllAdapter() {
        adapters = new SearchAdapter[3];
        adapters[0] = new JestarAdapter();
        adapters[1] = new QantasAdapter();
        adapters[2] = new VirginAdapter();
    }

    @Override
    public SearchResult search(String departure, String destination, LocalDateTime departTime, int numPassengers) {
        SearchResult result = new SearchResult(0);
        for(SearchAdapter adapter: adapters) {
            result.concat(adapter.search(departure,destination,departTime,numPassengers));
        }
        return result;
    }
}
