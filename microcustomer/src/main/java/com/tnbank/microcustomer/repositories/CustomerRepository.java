package com.tnbank.microcustomer.repositories;

import com.tnbank.microcustomer.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,String> {

}
