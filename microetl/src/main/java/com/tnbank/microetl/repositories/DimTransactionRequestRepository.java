package com.tnbank.microetl.repositories;

import com.tnbank.microetl.entities.DimTransactionRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DimTransactionRequestRepository extends CrudRepository<DimTransactionRequest, String> {
    List<DimTransactionRequest> findAllByTimestampAfter(@Param("timestamp") LocalDateTime timestamp);
}
