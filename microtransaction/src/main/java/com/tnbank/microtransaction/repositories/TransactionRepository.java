package com.tnbank.microtransaction.repositories;

import com.tnbank.microtransaction.entities.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction,String> {
}
