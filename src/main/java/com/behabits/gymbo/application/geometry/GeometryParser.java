package com.behabits.gymbo.application.geometry;

import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.geojson.GeoJsonReader;

@NoArgsConstructor
public class GeometryParser {

    public Geometry getGeometryFromWKT(String wkt) {
        WKTReader reader = new WKTReader(new GeometryFactory(new PrecisionModel(), 4326));
        try {
            return reader.read(wkt);
        } catch (Exception pe) {
            return null;
        }
    }

    public Geometry getGeometryFromGeoJson(Object geometry) {
        GeoJsonReader reader = new GeoJsonReader(new GeometryFactory(new PrecisionModel(), 4326));
        try {
            return reader.read(geometry.toString());
        } catch (Exception pe) {
            return null;
        }
    }

}
