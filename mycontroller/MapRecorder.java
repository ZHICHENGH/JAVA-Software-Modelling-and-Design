package mycontroller;

import mycontroller.TileAdapter.ITileAdapter;
import mycontroller.TileAdapter.TileAdapterFactory;
import tiles.MapTile;
import tiles.WaterTrap;
import utilities.Coordinate;
import world.World;

import java.util.*;


public class MapRecorder {
    /**
     * mapping between coordinates and map tile in World
     */
    private HashMap<Coordinate, ITileAdapter> coordinateTileMap;

    /**
     * mapping between tile type and coordinates
     */
    private HashMap<ITileAdapter.TileType, ArrayList<Coordinate>> tileTypeCoordinatesMap;

    /**
     * guide agent the status of the tile in the map
     */
    private HashMap<Coordinate, TileStatus> mapStatus;

    /**
     * mapping between tile type and car health cost
     */
    public static final Map<ITileAdapter.TileType, Float> TILE_HEALTH_COST_MAP = initMap();

    /**
     * @return mapping between tile type and car health cost object
     */
    private static Map<ITileAdapter.TileType, Float> initMap() {
        Map<ITileAdapter.TileType, Float> map = new HashMap<>();

        map.put(ITileAdapter.TileType.FINISH, 0f);
        map.put(ITileAdapter.TileType.START,  0f);
        map.put(ITileAdapter.TileType.HEALTH, 1.25f); // 5 (ICE) * 0.25
        map.put(ITileAdapter.TileType.LAVA,   -5f); // 20 (LAVA) * 0.25 (delta)
        map.put(ITileAdapter.TileType.PARCEL, 0f);
        map.put(ITileAdapter.TileType.ROAD,   0f);
        map.put(ITileAdapter.TileType.WALL,   Float.MIN_VALUE);
        map.put(ITileAdapter.TileType.WATER,  (float) WaterTrap.Yield);

        return Collections.unmodifiableMap(map);
    }

    /**
     * initialize map recorder object
     */
    public MapRecorder() {
        this.coordinateTileMap      = new HashMap<>();
        this.tileTypeCoordinatesMap = new HashMap<>();
        this.mapStatus              = new HashMap<>();
    }

    /**
     * update the world without trap information to the map recorder
     * @param initialMap : map without any trap information
     */
    public void updateInitialMap(HashMap<Coordinate, MapTile> initialMap) {
        for (Coordinate c : initialMap.keySet()) {
            MapTile t = initialMap.get(c);
            if (!t.isType(MapTile.Type.EMPTY)) {
                /* create adapter */
                ITileAdapter tileAdapter = TileAdapterFactory.getInstance()
                                                                .createTileAdapter(t);
                /* update two maps and tile status */
                updateMapEntry(c, tileAdapter);
                updateCoordinateInitialStatus(c, t);
            }
        }
    }

    /**
     * initially, wall is unreachable, roads are unexplored, others are explored
     * @param c : tile's coordinate
     * @param t : tile
     */
    private void updateCoordinateInitialStatus(Coordinate c, MapTile t) {
        /* Roads are unexplored tile now */
        if (t.isType(MapTile.Type.ROAD)) {
            mapStatus.put(c, TileStatus.UNEXPLORED);
        /* walls are unreachable */
        } else if (t.isType(MapTile.Type.WALL)) {
            mapStatus.put(c, TileStatus.UNREACHABLE);
        /* other MapTile (start, finish) are explored */
        } else {
            mapStatus.put(c, TileStatus.EXPLORED);
        }
    }

    /**
     * update the car's 9*9 observed tiles to the map
     * @param carView : 9*9 grid car view
     */
    public void updateMapRecorder(HashMap<Coordinate, MapTile> carView) {
        for (Coordinate c : carView.keySet()) {
            MapTile t = carView.get(c);
            /* EMPTY means this coordinate is not in the world, thus ignore it */
            if (!t.isType(MapTile.Type.EMPTY)) {
                ITileAdapter currentTileAdapter = TileAdapterFactory.getInstance()
                                                                    .createTileAdapter(t);
                /* update two maps and tile status */
                updateMapEntry(c, currentTileAdapter);
                updateCoordinateStatus(c, currentTileAdapter);
            }
        }
    }

    /**
     * @param tileType : the type of the tile adapter
     * @param c : tile's coordinate
     */
    private void putTileTypeCoordinatesMap(ITileAdapter.TileType tileType, Coordinate c) {
        tileTypeCoordinatesMap.putIfAbsent(tileType, new ArrayList<>());
        tileTypeCoordinatesMap.get(tileType).add(c);
    }

    /**
     * update non-wall tile with explored status
     * @param c : tile's coordinate
     * @param t : tile adapter
     */
    private void updateCoordinateStatus(Coordinate c, ITileAdapter t) {
        if (!t.isType(ITileAdapter.TileType.WALL)) {
            mapStatus.put(c, TileStatus.EXPLORED);
        }
    }

    /**
     * Update two hash map. First overwrite coordinate's MapTile. Second, if
     * there is an change in the MapTile type of the same coordinate, then there
     * is MapTile type change at that location. So, delete previous MapTile type
     * at that coordinate if any and add the new {coordinate: type} pair.
     * @param c : tile's coordinate
     * @param currentTileAdapter : current tile's adapter
     */
    private void updateMapEntry(Coordinate c, ITileAdapter currentTileAdapter) {
        ITileAdapter previousTileAdapter = this.coordinateTileMap.get(c);

        if (previousTileAdapter != null) {
            /* type of the tile has changed */
            if (!currentTileAdapter.isType(previousTileAdapter.getType())) {
                /* overwrite previous record */
                coordinateTileMap.put(c, currentTileAdapter);
                /* delete previous record */
                tileTypeCoordinatesMap.get(previousTileAdapter.getType())
                                        .remove(c);
                /* update new record */
                putTileTypeCoordinatesMap(currentTileAdapter.getType(), c);
            }
        /* new tile directly add */
        } else {
            coordinateTileMap.putIfAbsent(c, currentTileAdapter);
            putTileTypeCoordinatesMap(currentTileAdapter.getType(), c);
        }
    }

    /**
     * @param c : tile's coordinate to find its neighbor
     * @param allowableTileStatuses : neighbor coordinate allowed status
     * @return neighbor coordinates with allowed status
     */
    public ArrayList<Coordinate> tileNeighbors(Coordinate c, ArrayList<TileStatus> allowableTileStatuses) {

        ArrayList<Coordinate> neighbors = new ArrayList<>();

        /* right */
        if (c.x < World.MAP_WIDTH - 1) {
            Coordinate right = new Coordinate(c.x + 1, c.y);
            if (allowableTileStatuses.contains(mapStatus.get(right))) {
                neighbors.add(right);
            }
        }

        /* down */
        if (c.y > 0) {
            Coordinate down = new Coordinate(c.x, c.y - 1);
            if (allowableTileStatuses.contains(mapStatus.get(down))) {
                neighbors.add(down);
            }
        }

        /* left */
        if (c.x > 0) {
            Coordinate left = new Coordinate(c.x - 1, c.y);
            if (allowableTileStatuses.contains(mapStatus.get(left))) {
                neighbors.add(left);
            }
        }

        /* up */
        if (c.y < World.MAP_HEIGHT - 1) {
            Coordinate up = new Coordinate(c.x, c.y + 1);
            if (allowableTileStatuses.contains(mapStatus.get(up))) {
                neighbors.add(up);
            }
        }

        return neighbors;
    }

    /**
     * @param c : tile's coordinate
     * @return tile adapter
     */
    public ITileAdapter getTileAdapter(Coordinate c) {
        return coordinateTileMap.get(c);
    }

    /**
     * @param tileType : tile's adapter's type
     * @return list of coordinates with specified tile type
     */
    public ArrayList<Coordinate> getCoordinates(ITileAdapter.TileType tileType) {
        tileTypeCoordinatesMap.putIfAbsent(tileType, new ArrayList<>());
        return tileTypeCoordinatesMap.get(tileType);
    }

    /**
     * @return list of coordinates with unexplored tiles beside it
     */
    public ArrayList<Coordinate> getOutMostExploredCoordinates() {
        ArrayList<Coordinate> res = new ArrayList<>();

        for (Coordinate c: mapStatus.keySet()) {
            if (mapStatus.get(c) == TileStatus.EXPLORED) {
                ArrayList<Coordinate> neighbors = tileNeighbors(c, new ArrayList<>(Collections.singletonList(TileStatus.UNEXPLORED)));
                if (!neighbors.isEmpty() && !res.contains(c)){
                    res.add(c);
                }
            }
        }
        return res;
    }
}
