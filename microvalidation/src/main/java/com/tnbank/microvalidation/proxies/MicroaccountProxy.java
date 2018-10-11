package com.tnbank.microvalidation.proxies;

import com.tnbank.microvalidation.beans.AccountBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "microaccount", url = "localhost:9002")
public interface MicroaccountProxy {
    @RequestMapping(method = RequestMethod.GET, path = "/accounts/{id}")
    AccountBean getAccountById(@PathVariable("id") String ig);
}
