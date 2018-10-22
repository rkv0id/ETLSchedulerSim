package com.tnbank.microetl.repositories;

import com.tnbank.microetl.measures.AccountTypeMeasure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface AccountTypeMeasureRepository extends CrudRepository<AccountTypeMeasure, LocalDate> {
    AccountTypeMeasure findByDay(@Param("day") LocalDate day);
}
