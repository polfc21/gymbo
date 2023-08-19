package com.behabits.gymbo.application.geometry;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class GeometryParserTest {

    private final GeometryParser geometryParser = new GeometryParser();
    private final GeometryFactoryRepository geometryFactoryRepository = new GeometryFactoryRepository();

    @Test
    void givenWKTWhenGetGeometryFromWKTThenReturnGeometry() {
        String wkt = this.geometryFactoryRepository.getBarcelonaWKT();

        assertThat(this.geometryParser.getGeometryFromWKT(wkt).toText(), is(wkt));
    }

    @Test
    void givenGeoJsonWhenGetGeometryFromGeoJsonThenReturnGeometry() {
        Object geoJson = this.geometryFactoryRepository.getBarcelonaGeoJson();

        assertThat(this.geometryParser.getGeometryFromGeoJson(geoJson).toText(), is(this.geometryFactoryRepository.getBarcelonaWKT()));
    }

}
