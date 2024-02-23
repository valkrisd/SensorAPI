package org.springcourse.SensorAPI.util;

import org.springcourse.SensorAPI.models.Sensor;
import org.springcourse.SensorAPI.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorService;

    @Autowired
    public SensorValidator(SensorsService sensorService) {
        this.sensorService = sensorService;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor = (Sensor) o;
        if (sensorService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Sensor with this name already exists");
        }
    }


}
