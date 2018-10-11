package com.tnbank.microtransaction.repositories;

import com.tnbank.microtransaction.entities.TransactionRequest;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRequestRepository extends CrudRepository<TransactionRequest,String>{
}
