package com.tnbank.microetl.repositories;

import com.tnbank.microetl.entities.DimCustomer;
import org.springframework.data.repository.CrudRepository;

public interface DimCustomerRepository extends CrudRepository<DimCustomer, String> {
}
