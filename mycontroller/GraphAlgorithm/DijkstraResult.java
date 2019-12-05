package mycontroller.GraphAlgorithm;

import utilities.Coordinate;

import java.util.HashMap;
import java.util.Stack;


public class DijkstraResult implements ISearchResult {
    /**
     * mapping of coordinates from source to any place
     */
    private HashMap<Coordinate, Coordinate> cameFrom;

    /**
     * mapping for car status from source to any place
     */
    private HashMap<Coordinate, Node>       costSoFar;

    /**
     * The constructor for DijkstraResult.
     *
     * @param cameFrom :  mapping of coordinates from source to any place
     * @param costSoFar : mapping for car status from source to any place
     */
    public DijkstraResult(HashMap<Coordinate, Coordinate> cameFrom,
                          HashMap<Coordinate, Node>       costSoFar) {
        this.cameFrom  = cameFrom;
        this.costSoFar = costSoFar;
    }

    /**
     * @param destination : destination of coordinate to go to
     * @return the next coordinate in the path from source to destination
     */
    @Override
    public Coordinate getNext(Coordinate destination) {
        Stack<Coordinate> path = new Stack<>();

        /* backtrack the path */
        for (Coordinate c = destination; cameFrom.get(c) != null; c = cameFrom.get(c)) {

            assert(!path.contains(c));
            path.push(c);
        }

        /* destination is source */
        if (path.empty()) {
            return destination;
        /* the next coordinate */
        } else {
            return path.pop();
        }
    }

    /**
     * @return mapping for car status from source to any place
     */
    @Override
    public HashMap<Coordinate, Node> getCostSoFar() {
        return costSoFar;
    }

    /**
     * @return : mapping of coordinates from source to any place
     */
    @Override
    public HashMap<Coordinate, Coordinate> getCameFrom() {
        return cameFrom;
    }
}