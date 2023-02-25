package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.services.TrainingService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.TrainingResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.TrainingApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.TRAININGS)
@RestController
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    private final TrainingApiMapper mapper;

    @GetMapping
    public ResponseEntity<List<TrainingResponse>> findTrainingsByMonth(@RequestBody @Valid Month month) {
        List<TrainingResponse> trainings = this.trainingService.findTrainingsByMonth(month)
                .stream()
                .map(this.mapper::toResponse)
                .toList();
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping(ApiConstant.ID)
    public ResponseEntity<TrainingResponse> findTrainingById(@PathVariable Long id) {
        Training training = this.trainingService.findTrainingById(id);
        return new ResponseEntity<>(this.mapper.toResponse(training), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TrainingResponse> createTraining(@RequestBody @Valid TrainingRequest request) {
        Training training = this.trainingService.createTraining(this.mapper.toDomain(request));
        return new ResponseEntity<>(this.mapper.toResponse(training), HttpStatus.CREATED);
    }
}
