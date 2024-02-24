package org.springcourse.SensorAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springcourse.SensorAPI.dto.MeasurementDTO;
import org.springcourse.SensorAPI.dto.MeasurementsResponse;
import org.springcourse.SensorAPI.models.Measurement;
import org.springcourse.SensorAPI.services.MeasurementsService;
import org.springcourse.SensorAPI.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementValidator measurementValidator) {
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            String errorMsg =ErrorsUtil.getErrorMessage(bindingResult);
            // выбрасываем кастомное исключение в случае, если не получилось создать объект типа Measurement
            // далее необходимо обработать его в отдельном @ExceptionHandler
            throw new MeasurementNotCreatedException(errorMsg);
        }

        // сохраняем объект Sensor
        measurementsService.save(measurement);

        // возвращаем ResponseEntity с объектом Measurement и статусом CREATED
        return ResponseEntity.ok(HttpStatus.OK); // 201

    }

    @GetMapping
    public MeasurementsResponse getMeasurements() {
        return new MeasurementsResponse(measurementsService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementsService.rainyDaysCount();
    }


    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        // конвертируем MeasurementDTO в Measurement
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        // конвертируем Measurement в MeasurementDTO
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException exception) {
        // создаем объект SensorErrorResponse и устанавливаем в него сообщение об ошибке и текущее время
        MeasurementErrorResponse errorResponse = new MeasurementErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        // возвращаем ResponseEntity с объектом MeasurementErrorResponse
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
