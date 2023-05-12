package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.services.SerieService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.SERIES)
@RestController
@RequiredArgsConstructor
public class SerieController {

    private final SerieService serieService;


    @DeleteMapping(ApiConstant.ID)
    public ResponseEntity<String> deleteSerie(@PathVariable Long id) {
        this.serieService.deleteSerie(id);
        return new ResponseEntity<>("Serie with id " + id + " deleted successfully", HttpStatus.NO_CONTENT);
    }
}
