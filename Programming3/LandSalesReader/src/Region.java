import java.io.*;

public enum Region {
    STATE("State"),
    BORDER("Border"),
    MIDLAND("Midland"),
    WEST("West"),
    DUBLIN("Dublin"),
    MID_EAST("Mid-East"),
    MID_WEST("Mid-West"),
    SOUTH_EAST("South-East"),
    SOUTH_WEST("South-West");
    private final String region;

    Region(String region) {
        this.region = region;
    }

    //method returns appropriate enum from the provided string
    public static Region getRegionFromString(String region) {
        for(Region r : Region.values()) {
            if(r.getRegionName().equals(region)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Unknown region passed to getRegionFromString method");
    }
    String getRegionName() {
        return this.region;
    }

}
