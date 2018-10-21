package com.tnbank.agentui.proxies;

import com.tnbank.agentui.beans.CustomerBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "microcustomer", url = "localhost:9001")
public interface MicrocustomerProxy {
    @RequestMapping(method = RequestMethod.GET, path = "/customers")
    Resources<CustomerBean> getAllCustomers();
    @RequestMapping(method = RequestMethod.GET, path = "/customers/{id}")
    Resource<CustomerBean> getCustomerById(@PathVariable("id") String id);
}
