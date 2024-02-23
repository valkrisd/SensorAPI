package org.springcourse.SensorAPI.dto;

import java.util.List;

public class MeasurementsResponse {
    private List<MeasurementDTO> measurementDTOList;

    public MeasurementsResponse(List<MeasurementDTO> measurementDTOList) {
        this.measurementDTOList = measurementDTOList;
    }

    public List<MeasurementDTO> getMeasurementDTOList() {
        return measurementDTOList;
    }

    public void setMeasurementDTOList(List<MeasurementDTO> measurementDTOList) {
        this.measurementDTOList = measurementDTOList;
    }
}
