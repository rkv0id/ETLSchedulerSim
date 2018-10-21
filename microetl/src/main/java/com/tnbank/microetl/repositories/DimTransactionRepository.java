package com.tnbank.microetl.repositories;

import com.tnbank.microetl.entities.DimTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DimTransactionRepository extends CrudRepository<DimTransaction, String> {
    List<DimTransaction> findAllByTypeCode(@Param("typeCode") String typeCode);
    List<DimTransaction> findAllByTypeCodeAndTimestampAfter(@Param("typeCode") String typeCode, @Param("timestamp") LocalDateTime timestamp);
    List<DimTransaction> findAllByTimestampAfter(@Param("timestamp") LocalDateTime timestamp);
}
