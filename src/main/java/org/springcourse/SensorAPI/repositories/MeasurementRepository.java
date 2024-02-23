package org.springcourse.SensorAPI.repositories;

import org.springcourse.SensorAPI.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    long countAllByIsRainingTrue();
}
