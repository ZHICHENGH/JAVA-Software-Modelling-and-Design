package mycontroller.TileAdapter;


public interface ITileAdapter {
    /**
     * types of tiles used in the system
     */
    enum TileType{WALL, ROAD, START, FINISH, HEALTH, LAVA, PARCEL, WATER}

    /**
     * @return : TileType of the object
     */
    TileType getType();

    /**
     * @param tileType : TileType
     * @return : true if object is input TileType otherwise false
     */
    boolean isType(TileType tileType);
}
