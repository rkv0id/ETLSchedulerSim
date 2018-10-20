package com.tnbank.microtransaction.proxies;

import com.tnbank.microtransaction.beans.AccountBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Service
@FeignClient(name = "microaccount", url = "localhost:9002")
public interface MicroaccountProxy {
    @RequestMapping(method = RequestMethod.GET, path = "/accounts")
    List<AccountBean> getAllAccounts();
    @RequestMapping(method = RequestMethod.GET, path = "/accounts/search/findAllByCinCustomer?cinCustomer={cin}")
    List<AccountBean> getAccountsByCustomer(@PathVariable("cin") String cin);
    @RequestMapping(method = RequestMethod.GET, path = "/accounts/{id}")
    AccountBean getAccountById(@PathVariable("id") String id);
    @RequestMapping(method = RequestMethod.POST, path = "/accounts")
    void saveAccount(@RequestBody AccountBean accountBean);
}
