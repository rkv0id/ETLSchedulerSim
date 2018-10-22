package com.tnbank.agentui.proxies;

import com.tnbank.agentui.beans.AccountBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microaccount")
public interface MicroaccountProxy {
    @GetMapping(value = "/microaccount/accounts")
    Resources<AccountBean> getAllAccounts();
    @GetMapping(value = "/microaccount/accounts/search/findAllByCinCustomer?cinCustomer={cin}")
    Resources<AccountBean> getAccountsByCustomer(@PathVariable("cin") String cin);
    @GetMapping(value = "/microaccount/accounts/{id}")
    Resource<AccountBean> getAccountById(@PathVariable("id") String id);
}
