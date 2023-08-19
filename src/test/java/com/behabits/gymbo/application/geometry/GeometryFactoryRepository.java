package com.behabits.gymbo.application.geometry;

import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.WKTReader;

@NoArgsConstructor
public class GeometryFactoryRepository {

    private final WKTReader reader = new WKTReader(new GeometryFactory(new PrecisionModel(), 4326));

    public Geometry getBarcelonaGeometry() {
        try {
            return this.reader.read(this.getBarcelonaWKT());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getBarcelonaWKT() {
        return "POINT (2.154007 41.390205)";
    }

    public Object getBarcelonaGeoJson() {
        return """
                {
                  "type": "Point",
                  "coordinates": [
                    2.154007,
                    41.390205
                  ]
                }""";
    }

}
