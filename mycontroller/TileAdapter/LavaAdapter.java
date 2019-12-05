package mycontroller.TileAdapter;

import tiles.MapTile;


public class LavaAdapter implements ITileAdapter {
    /**
     * mapTile adaptee
     */
    private MapTile lavaTile;

    /**
     * @param lavaTile : mapTile with lava type
     */
    public LavaAdapter(MapTile lavaTile) {
        this.lavaTile = lavaTile;
    }

    /**
     * @return : LAVA tile type
     */
    @Override
    public TileType getType() {
        return TileType.LAVA;
    }

    /**
     * @param tileType : TileType
     * @return : true if input TileType is LAVA otherwise false
     */
    @Override
    public boolean isType(TileType tileType) {
        return tileType == TileType.LAVA;
    }
}
