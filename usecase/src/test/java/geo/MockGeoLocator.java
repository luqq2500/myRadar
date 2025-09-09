package geo;

import geo.api.GeoLocator;

public class MockGeoLocator implements GeoLocator {
    @Override
    public Coordinate getCoordinate() {
        return new Coordinate(89.0, 99.0);
    }
}
