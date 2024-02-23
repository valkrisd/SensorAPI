package org.springcourse.SensorAPI.services;

import org.springcourse.SensorAPI.dto.SensorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springcourse.SensorAPI.models.Sensor;
import org.springcourse.SensorAPI.repositories.SensorRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorsService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        sensorRepository.save(sensor);
    }

}
