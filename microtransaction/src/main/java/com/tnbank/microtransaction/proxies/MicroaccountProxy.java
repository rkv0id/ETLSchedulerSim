package com.tnbank.microtransaction.proxies;

import com.tnbank.microtransaction.beans.AccountBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@FeignClient(name = "zuul-server")
@RibbonClient(name = "microaccount")
public interface MicroaccountProxy {
    @GetMapping(value = "/microaccount/accounts")
    List<AccountBean> getAllAccounts();
    @GetMapping(value = "/microaccount/accounts/search/findAllByCinCustomer?cinCustomer={cin}")
    List<AccountBean> getAccountsByCustomer(@PathVariable("cin") String cin);
    @GetMapping(value = "/microaccount/accounts/{id}")
    AccountBean getAccountById(@PathVariable("id") String id);
    @PostMapping(value = "/microaccount/accounts")
    void saveAccount(@RequestBody AccountBean accountBean);
}
