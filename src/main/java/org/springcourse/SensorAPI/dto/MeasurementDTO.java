package org.springcourse.SensorAPI.dto;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class MeasurementDTO {

    @Valid
    private Sensor sensor;

    public static class Sensor {
        @NotBlank(message = "Sensor name should not be empty")
        private String name;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @NotNull(message = "Value should not be empty")
    @Range(min = -100, max = 100, message = "Value should be between -100 and 100")
    @Digits(integer = 3, fraction = 2, message = "Value should be at most 5 digits long")
    private double value;

    @Column(name = "is_raining")
    @NotNull(message = "isRaining should not be empty")
    private boolean isRaining;

    public MeasurementDTO() {

    }

    public MeasurementDTO(Sensor sensor, double value, boolean isRaining) {
        this.sensor = sensor;
        this.value = value;
        this.isRaining = isRaining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean getIsRaining() {
        return isRaining;
    }

    public void setIsRaining(boolean raining) {
        isRaining = raining;
    }
}
