package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Training;
import com.behabits.gymbo.domain.services.TrainingService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.TrainingRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.TrainingResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.TrainingApiMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.Year;
import java.util.List;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.TRAININGS)
@RestController
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    private final TrainingApiMapper mapper;

    @GetMapping
    public ResponseEntity<List<TrainingResponse>> findTrainingsByMonthAndYear(@RequestParam @Valid @NotNull Month month,
                                                                              @RequestParam @Valid @NotNull Year year) {
        List<TrainingResponse> trainings = this.trainingService.findTrainingsByMonthAndYear(month, year)
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

    @PutMapping
    public ResponseEntity<TrainingResponse> updateTraining(@RequestBody @Valid TrainingRequest request) {
        Training training = this.trainingService.updateTraining(this.mapper.toDomain(request));
        return new ResponseEntity<>(this.mapper.toResponse(training), HttpStatus.OK);
    }

    @DeleteMapping(ApiConstant.ID)
    public ResponseEntity<String> deleteTraining(@PathVariable Long id) {
        this.trainingService.deleteTraining(id);
        return new ResponseEntity<>("Training with id " + id + " deleted successfully", HttpStatus.NO_CONTENT);
    }
}
