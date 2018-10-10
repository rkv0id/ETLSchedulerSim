package com.tnbank.agentui.proxies;

import com.tnbank.agentui.beans.AccountBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "microaccount", url = "localhost:9002")
public interface MicroaccountProxy {
    @RequestMapping(method = RequestMethod.GET, path = "/accounts")
    Resources<AccountBean> getAllAccounts();
    @RequestMapping(method = RequestMethod.GET, path = "/accounts/search/findAllByCinCustomer?cinCustomer={cin}")
    Resources<AccountBean> getAccountsByCustomer(@PathVariable("cin") String cin);
}
