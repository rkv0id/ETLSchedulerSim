package com.tnbank.microetl.repositories;

import com.tnbank.microetl.measures.RequestDayMeasure;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface RequestDayMeasureRepository extends CrudRepository<RequestDayMeasure, LocalDate> {
}
