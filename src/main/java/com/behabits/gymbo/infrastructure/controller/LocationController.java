package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Location;
import com.behabits.gymbo.domain.services.LocationService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.LocationRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.LocationResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.LocationApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.LOCATIONS)
@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final LocationApiMapper mapper;

    @GetMapping(ApiConstant.ID)
    public ResponseEntity<LocationResponse> findLocationById(@PathVariable Long id) {
        Location location = this.locationService.findLocationById(id);
        return new ResponseEntity<>(this.mapper.toResponse(location), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@RequestBody @Valid LocationRequest request) {
        Location location = this.locationService.createLocation(this.mapper.toDomain(request));
        return new ResponseEntity<>(this.mapper.toResponse(location), HttpStatus.CREATED);
    }

    @PutMapping(ApiConstant.ID)
    public ResponseEntity<LocationResponse> updateLocation(@PathVariable Long id, @RequestBody @Valid LocationRequest request) {
        Location locationToUpdate = this.mapper.toDomain(request);
        Location locationUpdated = this.locationService.updateLocation(id, locationToUpdate);
        return new ResponseEntity<>(this.mapper.toResponse(locationUpdated), HttpStatus.OK);
    }

    @DeleteMapping(ApiConstant.ID)
    public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
        this.locationService.deleteLocation(id);
        return new ResponseEntity<>("Location with id " + id + " deleted successfully", HttpStatus.NO_CONTENT);
    }
}
