package mycontroller.TileAdapter;

import tiles.MapTile;

public class RoadAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile roadTile;

    /**
     * @param roadTile : mapTile with road type
     */
    public RoadAdapter(MapTile roadTile) {
        this.roadTile = roadTile;
    }

    /**
     * @return : ROAD tile type
     */
    @Override
    public TileType getType() {
        return TileType.ROAD;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is ROAD otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.ROAD;
    }
}
