package com.tnbank.microaccount.repositories;

import com.tnbank.microaccount.entities.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account,String> {
    List<Account> findAllByCinCustomer(@Param("cinCustomer") String cinCustomer);
}
