import java.time.LocalDateTime;
import java.util.Objects;

public class Flight implements Comparable<Flight>{
    String departure;
    String destination;
    double airfare;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;

    public Flight(String departure, String destination, double airfare, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.departure = departure;
        this.destination = destination;
        this.airfare = airfare;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String from() {
        return this.departure;
    }

    public String to() {
        return this.destination;
    }

    public double fare() {
        return this.airfare;
    }

    public LocalDateTime departAt() {
        return this.departureTime;
    }

    public LocalDateTime arriveAt() {
        return this.arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Double.compare(flight.airfare, airfare) == 0 &&
                departure.equals(flight.departure) &&
                destination.equals(flight.destination) &&
                departureTime.equals(flight.departureTime) &&
                arrivalTime.equals(flight.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departure, destination, airfare, departureTime, arrivalTime);
    }

    @Override
    public int compareTo(Flight o) {
        return this.airfare < o.airfare? -1:1;
    }

}