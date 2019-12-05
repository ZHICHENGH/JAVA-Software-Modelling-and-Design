package mycontroller.TileAdapter;

import tiles.MapTile;

public class StartAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile startTile;

    /**
     * @param startTile : mapTile with start type
     */
    public StartAdapter(MapTile startTile) {
        this.startTile = startTile;
    }

    /**
     * @return : START tile type
     */
    @Override
    public TileType getType() {
        return TileType.START;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is START otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.START;
    }
}
