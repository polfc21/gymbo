package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.exceptions.NotFoundException;
import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.domain.repositories.LocationModelRepository;
import com.behabits.gymbo.domain.services.LocationService;
import com.behabits.gymbo.domain.services.TokenService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.LocationRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.LocationResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.LocationApiMapper;
import com.behabits.gymbo.infrastructure.controller.repositories.request.LocationRequestRepository;
import com.behabits.gymbo.infrastructure.controller.repositories.response.LocationResponseRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(LocationController.class)
class LocationControllerTest {

    @MockBean
    private LocationService locationService;

    @MockBean
    private LocationApiMapper mapper;

    @MockBean
    private TokenService tokenService; // Added to load application context

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final LocationRequest wktLocationRequest = new LocationRequestRepository().getWKTLocationRequest();

    private final LocationRequest geoJsonLocationRequest = new LocationRequestRepository().getGeoJsonLocationRequest();

    private final LocationRequest incorrectLocationRequest = new LocationRequestRepository().getIncorrectLocationRequest();

    private final Location location = new LocationModelRepository().getBarcelona();

    private final LocationResponse locationResponse = new LocationResponseRepository().getLocationResponse();

    @Test
    @WithMockUser
    void givenExistentIdWhenFindLocationByIdThenReturnLocation() throws Exception {
        Long existentId = 1L;

        given(this.locationService.findLocationById(existentId)).willReturn(this.location);
        given(this.mapper.toResponse(any(Location.class))).willReturn(this.locationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.LOCATIONS + ApiConstant.ID, existentId)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        assertThat(response.getContentAsString(), is(this.objectMapper.writeValueAsString(this.locationResponse)));
    }

    @Test
    @WithMockUser
    void givenNonExistentIdWhenFindLocationByIdThenReturn404() throws Exception {
        Long nonExistentId = 1L;

        given(this.locationService.findLocationById(nonExistentId)).willThrow(NotFoundException.class);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.LOCATIONS + ApiConstant.ID, nonExistentId)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenUserWithoutPermissionsWhenFindLocationByIdThenReturn403() throws Exception {
        Long existentId = 1L;

        given(this.locationService.findLocationById(existentId)).willReturn(this.location);
        given(this.mapper.toResponse(any(Location.class))).willReturn(this.locationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                get(ApiConstant.API_V1 + ApiConstant.LOCATIONS + ApiConstant.ID, existentId)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.UNAUTHORIZED.value()));
    }

    @Test
    @WithMockUser
    void givenWktLocationRequestWhenCreateLocationThenReturnLocationResponseAnd201() throws Exception {
        given(this.mapper.toDomain(this.wktLocationRequest)).willReturn(this.location);
        given(this.locationService.createLocation(this.location)).willReturn(this.location);
        given(this.mapper.toResponse(this.location)).willReturn(this.locationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.LOCATIONS)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.wktLocationRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }

    @Test
    @WithMockUser
    void givenGeoJsonLocationRequestWhenCreateLocationThenReturnLocationResponseAnd201() throws Exception {
        given(this.mapper.toDomain(this.geoJsonLocationRequest)).willReturn(this.location);
        given(this.locationService.createLocation(this.location)).willReturn(this.location);
        given(this.mapper.toResponse(this.location)).willReturn(this.locationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.LOCATIONS)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.geoJsonLocationRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.CREATED.value()));
    }

    @Test
    @WithMockUser
    void givenIncorrectLocationRequestWhenCreateLocationThenReturn400() throws Exception {
        given(this.mapper.toDomain(this.incorrectLocationRequest)).willReturn(this.location);
        given(this.locationService.createLocation(this.location)).willReturn(this.location);
        given(this.mapper.toResponse(this.location)).willReturn(this.locationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.LOCATIONS)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.incorrectLocationRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenUserWithoutPermissionsWhenCreateLocationThenReturn403() throws Exception {
        given(this.mapper.toDomain(this.wktLocationRequest)).willReturn(this.location);
        given(this.locationService.createLocation(this.location)).willReturn(this.location);
        given(this.mapper.toResponse(this.location)).willReturn(this.locationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                post(ApiConstant.API_V1 + ApiConstant.LOCATIONS)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.wktLocationRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenExistentIdAndWktLocationRequestWhenUpdateLocationThenReturnLocationResponseAnd200() throws Exception {
        Long existentId = 1L;

        given(this.mapper.toDomain(this.wktLocationRequest)).willReturn(this.location);
        given(this.locationService.updateLocation(existentId, this.location)).willReturn(this.location);
        given(this.mapper.toResponse(this.location)).willReturn(this.locationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.LOCATIONS + ApiConstant.ID, existentId)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.wktLocationRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    @WithMockUser
    void givenExistentIdAndGeoJsonLocationRequestWhenUpdateLocationThenReturnLocationResponseAnd200() throws Exception {
        Long existentId = 1L;

        given(this.mapper.toDomain(this.geoJsonLocationRequest)).willReturn(this.location);
        given(this.locationService.updateLocation(existentId, this.location)).willReturn(this.location);
        given(this.mapper.toResponse(this.location)).willReturn(this.locationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.LOCATIONS + ApiConstant.ID, existentId)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.geoJsonLocationRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    @WithMockUser
    void givenIncorrectLocationRequestWhenUpdateLocationThenReturn400() throws Exception {
        Long existentId = 1L;

        given(this.mapper.toDomain(this.incorrectLocationRequest)).willReturn(this.location);
        given(this.locationService.updateLocation(existentId, this.location)).willReturn(this.location);
        given(this.mapper.toResponse(this.location)).willReturn(this.locationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.LOCATIONS + ApiConstant.ID, existentId)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.incorrectLocationRequest))
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenUserWithoutPermissionsWhenUpdateLocationThenReturn403() throws Exception {
        Long existentId = 1L;

        given(this.mapper.toDomain(this.wktLocationRequest)).willReturn(this.location);
        given(this.locationService.updateLocation(existentId, this.location)).willReturn(this.location);
        given(this.mapper.toResponse(this.location)).willReturn(this.locationResponse);

        MockHttpServletResponse response = this.mockMvc.perform(
                put(ApiConstant.API_V1 + ApiConstant.LOCATIONS + ApiConstant.ID, existentId)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(this.wktLocationRequest))
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }

    @Test
    @WithMockUser
    void givenExistentIdWhenDeleteLocationThenReturn204() throws Exception {
        Long existentId = 1L;
        doNothing().when(this.locationService).deleteLocation(existentId);

        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.LOCATIONS + ApiConstant.ID, existentId)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    @WithMockUser
    void givenNonExistentIdWhenDeleteLocationThenReturn404() throws Exception {
        Long nonExistentId = 1L;
        doThrow(NotFoundException.class).when(this.locationService).deleteLocation(nonExistentId);

        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.LOCATIONS + ApiConstant.ID, nonExistentId)
                        .with(csrf())
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void givenUserWithoutPermissionsWhenDeleteLocationThenReturn403() throws Exception {
        Long existentId = 1L;
        doNothing().when(this.locationService).deleteLocation(existentId);

        MockHttpServletResponse response = this.mockMvc.perform(
                delete(ApiConstant.API_V1 + ApiConstant.LOCATIONS + ApiConstant.ID, existentId)
        ).andReturn().getResponse();

        assertThat(response.getStatus(), is(HttpStatus.FORBIDDEN.value()));
    }
}
