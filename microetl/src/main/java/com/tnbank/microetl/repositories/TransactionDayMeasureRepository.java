package com.tnbank.microetl.repositories;

import com.tnbank.microetl.measures.TransactionDayMeasure;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface TransactionDayMeasureRepository extends CrudRepository<TransactionDayMeasure, LocalDate> {
}
