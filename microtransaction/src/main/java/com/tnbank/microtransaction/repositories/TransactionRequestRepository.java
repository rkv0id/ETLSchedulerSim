package com.tnbank.microtransaction.repositories;

import com.tnbank.microtransaction.entities.TransactionRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRequestRepository extends CrudRepository<TransactionRequest,String>{
    List<TransactionRequest> findAllByStatusEquals(@Param("status") String status);
}
