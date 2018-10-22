package com.tnbank.agentui.proxies;

import com.tnbank.agentui.beans.CustomerBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microcustomer")
public interface MicrocustomerProxy {
    @GetMapping(value = "/microcustomer/customers")
    Resources<CustomerBean> getAllCustomers();
    @GetMapping(value = "/microcustomer/customers/{id}")
    Resource<CustomerBean> getCustomerById(@PathVariable("id") String id);
}
