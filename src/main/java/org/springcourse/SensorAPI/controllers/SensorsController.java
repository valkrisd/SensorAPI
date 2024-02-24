package org.springcourse.SensorAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springcourse.SensorAPI.dto.SensorDTO;
import org.springcourse.SensorAPI.models.Sensor;
import org.springcourse.SensorAPI.services.SensorsService;
import org.springcourse.SensorAPI.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody над каждым методом
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()) {
            String errorMsg = ErrorsUtil.getErrorMessage(bindingResult);

            // выбрасываем кастомное исключение в случае, если не получилось создать объект типа Sensor
            // далее необходимо обработать его в отдельном @ExceptionHandler
            throw new SensorNotCreatedException(errorMsg);
        }

        // сохраняем объект Sensor
        sensorsService.save(sensor);

        // возвращаем ResponseEntity с объектом Sensor и статусом CREATED
        return ResponseEntity.ok(HttpStatus.OK); // 201
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        // конвертируем SensorDTO в Sensor
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException exception) {
        // создаем объект SensorErrorResponse и устанавливаем в него сообщение об ошибке и текущее время
        SensorErrorResponse errorResponse = new SensorErrorResponse(
                exception.getMessage(),
                System.currentTimeMillis()
        );
        // возвращаем ResponseEntity с объектом SensorErrorResponse
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}




