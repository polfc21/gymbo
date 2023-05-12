package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.services.SerieService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.response.SerieResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.SerieApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.SERIES)
@RestController
@RequiredArgsConstructor
public class SerieController {

    private final SerieService serieService;
    private final SerieApiMapper mapper;

    @GetMapping(ApiConstant.ID)
    public ResponseEntity<SerieResponse> findSerieById(@PathVariable Long id) {
        Serie serie = this.serieService.findSerieById(id);
        return new ResponseEntity<>(this.mapper.toResponse(serie), HttpStatus.OK);
    }

    @DeleteMapping(ApiConstant.ID)
    public ResponseEntity<String> deleteSerie(@PathVariable Long id) {
        this.serieService.deleteSerie(id);
        return new ResponseEntity<>("Serie with id " + id + " deleted successfully", HttpStatus.NO_CONTENT);
    }
}
