package geo.api;

import geo.DistanceMeasurerDetails;

public interface GeoDistanceMeasurer {
    double measure(DistanceMeasurerDetails details);
}
