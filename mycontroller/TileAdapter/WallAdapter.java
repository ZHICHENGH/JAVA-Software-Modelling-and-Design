package mycontroller.TileAdapter;

import tiles.MapTile;


public class WallAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile wallTile;

    /**
     * @param wallTile : mapTile with wall type
     */
    public WallAdapter(MapTile wallTile) {
        this.wallTile = wallTile;
    }

    /**
     * @return : WALL tile type
     */
    @Override
    public TileType getType() {
        return TileType.WALL;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is WALL otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.WALL;
    }
}
