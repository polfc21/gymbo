package com.behabits.gymbo.application.geometry;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class GeometryCheckerTest {

    private final GeometryChecker geometryChecker = new GeometryChecker();
    private final GeometryFactoryRepository geometryFactoryRepository = new GeometryFactoryRepository();

    @Test
    void givenWKTWhenIsWKTThenReturnTrue() {
        Object wkt = this.geometryFactoryRepository.getBarcelonaWKT();

        assertThat(this.geometryChecker.isWKT(wkt), is(true));
    }

    @Test
    void givenGeoJsonWhenIsWKTThenReturnFalse() {
        Object geoJson = this.geometryFactoryRepository.getBarcelonaGeoJson();

        assertThat(this.geometryChecker.isWKT(geoJson), is(false));
    }

    @Test
    void givenWKTWhenIsGeoJsonThenReturnFalse() {
        assertThat(this.geometryChecker.isGeoJson("POINT (2.154007 41.390205)"), is(false));
    }

    @Test
    void givenGeoJsonWhenIsGeoJsonThenReturnTrue() {
        Object geoJson = this.geometryFactoryRepository.getBarcelonaGeoJson();

        assertThat(this.geometryChecker.isGeoJson(geoJson), is(true));
    }

}
