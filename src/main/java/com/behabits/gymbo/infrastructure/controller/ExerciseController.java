package com.behabits.gymbo.infrastructure.controller;

import com.behabits.gymbo.domain.models.Exercise;
import com.behabits.gymbo.domain.services.ExerciseService;
import com.behabits.gymbo.infrastructure.controller.constant.ApiConstant;
import com.behabits.gymbo.infrastructure.controller.dto.request.ExerciseRequest;
import com.behabits.gymbo.infrastructure.controller.dto.response.ExerciseResponse;
import com.behabits.gymbo.infrastructure.controller.mapper.ExerciseApiMapper;
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
}
