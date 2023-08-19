package com.behabits.gymbo.application.geometry;

import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.geojson.GeoJsonReader;

@NoArgsConstructor
public class GeometryChecker {

    public boolean isWKT(Object geometry) {
        WKTReader reader = new WKTReader(new GeometryFactory(new PrecisionModel(), 4326));
        try {
            return reader.read(geometry.toString()) != null;
        } catch (Exception pe) {
            return false;
        }
    }

    public boolean isGeoJson(Object geometry) {
        GeoJsonReader reader = new GeoJsonReader(new GeometryFactory(new PrecisionModel(), 4326));
        try {
            return reader.read(geometry.toString()) != null;
        } catch (ParseException e) {
            return false;
        }
    }

}
