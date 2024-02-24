package org.springcourse.SensorAPI.dto;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class MeasurementDTO {

    @Valid
    private SensorDTO sensor;

    @NotNull(message = "Value should not be empty")
    @Range(min = -100, max = 100, message = "Value should be between -100 and 100")
    @Digits(integer = 3, fraction = 2, message = "Value should be at most 5 digits long")
    private Double value;

    @Column(name = "is_raining")
    @NotNull(message = "isRaining should not be empty")
    private Boolean isRaining;

    public MeasurementDTO() {

    }

    public MeasurementDTO(SensorDTO sensor, Double value, Boolean isRaining) {
        this.sensor = sensor;
        this.value = value;
        this.isRaining = isRaining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getIsRaining() {
        return isRaining;
    }

    public void setIsRaining(Boolean raining) {
        isRaining = raining;
    }
}
