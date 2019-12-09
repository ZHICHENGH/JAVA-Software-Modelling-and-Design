import java.time.LocalDateTime;

public interface SearchAdapter {
    SearchResult search(String departure, String destination, LocalDateTime departTime, int numPassengers);
}
