package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.models.Serie;
import com.behabits.gymbo.domain.services.ExerciseService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import com.behabits.gymbo.infrastructure.controller.dto.request.SerieRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ExerciseResponse;
import com.behabits.gymbo.infrastructure.controller.dto.response.SerieResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.ExerciseApiMapper;
import com.behabits.gymbo.infrastructure.controller.mapper.SerieApiMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(ApiConstant.API_V1 + ApiConstant.EXERCISES)
@RestController
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    private final ExerciseApiMapper mapper;

    private final SerieApiMapper serieMapper;

    @GetMapping(ApiConstant.ID)
    public ResponseEntity<ExerciseResponse> findExerciseById(@PathVariable Long id) {
        Exercise exercise = this.exerciseService.findExerciseById(id);
        return new ResponseEntity<>(this.mapper.toResponse(exercise), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExerciseResponse> createExercise(@RequestBody @Valid ExerciseRequest request) {
        Exercise exercise = this.exerciseService.createExercise(this.mapper.toDomain(request));
        return new ResponseEntity<>(this.mapper.toResponse(exercise), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> findExercisesByTrainingId(@RequestParam(required = false) Long trainingId) {
        List<Exercise> exercises = this.exerciseService.findExercisesByTrainingId(trainingId);
        return new ResponseEntity<>(this.mapper.toResponse(exercises), HttpStatus.OK);
    }

    @GetMapping(ApiConstant.ID + ApiConstant.SERIES)
    public ResponseEntity<List<SerieResponse>> findSeriesByExerciseId(@PathVariable Long id) {
        List<SerieResponse> series = this.serieMapper.toResponse(this.exerciseService.findSeriesByExerciseId(id));
        return new ResponseEntity<>(series, HttpStatus.OK);
    }

    @PostMapping(ApiConstant.ID + ApiConstant.SERIES)
    public ResponseEntity<SerieResponse> createSerie(@PathVariable Long id, @RequestBody @Valid SerieRequest request) {
        Serie serie = this.exerciseService.createSerie(id, this.serieMapper.toDomain(request));
        return new ResponseEntity<>(this.serieMapper.toResponse(serie), HttpStatus.CREATED);
    }

    @DeleteMapping(ApiConstant.ID)
    public ResponseEntity<String> deleteExercise(@PathVariable Long id) {
        this.exerciseService.deleteExercise(id);
        return new ResponseEntity<>("Exercise with id " + id + " deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PutMapping(ApiConstant.ID)
    public ResponseEntity<ExerciseResponse> updateExercise(@PathVariable Long id, @RequestBody @Valid ExerciseRequest request) {
        Exercise exercise = this.exerciseService.updateExercise(id, this.mapper.toDomain(request));
        return new ResponseEntity<>(this.mapper.toResponse(exercise), HttpStatus.OK);
    }
}
