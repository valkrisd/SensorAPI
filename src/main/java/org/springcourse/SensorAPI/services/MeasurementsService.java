package org.springcourse.SensorAPI.services;

import org.springcourse.SensorAPI.models.Measurement;
import org.springcourse.SensorAPI.repositories.MeasurementRepository;
import org.springcourse.SensorAPI.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementsService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementsService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);

        measurementRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement) {
        // мы должна сами найти сенсор из БД по имени и вставить объект из Hibernate persistence context'а
        measurement.setSensor(sensorRepository.findByName(measurement.getSensor().getName()).get());
        measurement.setCreatedAt(LocalDateTime.now());
    }

    public long rainyDaysCount() {
        return measurementRepository.countAllByIsRainingTrue();
    }
}
