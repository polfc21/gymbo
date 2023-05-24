package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.services.SerieService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.SerieRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.SerieResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.SerieApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(ApiConstant.API_V1)
@RestController
@RequiredArgsConstructor
public class SerieController {

    private final SerieService serieService;
    private final SerieApiMapper mapper;

    @GetMapping(ApiConstant.SERIES + ApiConstant.ID)
    public ResponseEntity<SerieResponse> findSerieById(@PathVariable Long id) {
        Serie serie = this.serieService.findSerieById(id);
        return new ResponseEntity<>(this.mapper.toResponse(serie), HttpStatus.OK);
    }

    @PutMapping(ApiConstant.SERIES + ApiConstant.ID)
    public ResponseEntity<SerieResponse> updateSerie(@PathVariable Long id, @Valid @RequestBody SerieRequest serieRequest) {
        Serie serie = this.serieService.updateSerie(id, this.mapper.toDomain(serieRequest));
        return new ResponseEntity<>(this.mapper.toResponse(serie), HttpStatus.OK);
    }

    @DeleteMapping(ApiConstant.SERIES + ApiConstant.ID)
    public ResponseEntity<String> deleteSerie(@PathVariable Long id) {
        this.serieService.deleteSerie(id);
        return new ResponseEntity<>("Serie with id " + id + " deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping(ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES)
    public ResponseEntity<List<SerieResponse>> findSeriesByExerciseId(@PathVariable Long id) {
        List<SerieResponse> series = this.mapper.toResponse(this.serieService.findSeriesByExerciseId(id));
        return new ResponseEntity<>(series, HttpStatus.OK);
    }

    @PostMapping(ApiConstant.EXERCISES + ApiConstant.ID + ApiConstant.SERIES)
    public ResponseEntity<SerieResponse> createSerie(@PathVariable Long id, @RequestBody @Valid SerieRequest request) {
        Serie serie = this.serieService.createSerie(id, this.mapper.toDomain(request));
        return new ResponseEntity<>(this.mapper.toResponse(serie), HttpStatus.CREATED);
    }
}
