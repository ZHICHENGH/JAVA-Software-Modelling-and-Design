package mycontroller.TileAdapter;

import tiles.MapTile;
import tiles.TrapTile;


public class TileAdapterFactory {
    /**
     * singleton tile adapter factory instance
     */
    private static TileAdapterFactory tileAdapterFactory = null;

    /**
     * @return : singleton tile adapter factory instance
     */
    public static TileAdapterFactory getInstance() {
        if (tileAdapterFactory == null) {
            tileAdapterFactory = new TileAdapterFactory();
        }

        return tileAdapterFactory;
    }

    /**
     * @param mapTile : mapTile in the system
     * @return : adapter for the input mapTile
     */
    public ITileAdapter createTileAdapter(MapTile mapTile) {
        switch (mapTile.getType()) {
            case START:
                return new StartAdapter(mapTile);
            case FINISH:
                return new FinishAdapter(mapTile);
            case ROAD:
                return new RoadAdapter(mapTile);
            case WALL:
                return new WallAdapter(mapTile);
            case TRAP:
                switch (((TrapTile)mapTile).getTrap()) {
                    case "health":
                        return new HealthAdapter(mapTile);
                    case "lava":
                        return new LavaAdapter(mapTile);
                    case "parcel":
                        return new ParcelAdapter(mapTile);
                    case "water":
                        return new WaterAdapter(mapTile);
                }
        }

        /* for completeness */
        System.out.println("Unsupported tile type!");
        System.exit(1);
        return null;
    }

}
