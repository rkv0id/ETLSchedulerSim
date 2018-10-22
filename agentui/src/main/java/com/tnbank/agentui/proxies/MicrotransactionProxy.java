package com.tnbank.agentui.proxies;

import com.tnbank.agentui.beans.TransactionRequestBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microtransaction")
public interface MicrotransactionProxy {
    @GetMapping(value = "/microtransaction/transactionRequests")
    Resources<TransactionRequestBean> getAllTransactionRequests();
    @GetMapping(value = "/microtransaction/transactionRequests/{id}")
    Resource<TransactionRequestBean> getTransactionRequestById(@PathVariable("id") String id);
    @PostMapping(value = "/microtransaction/transactionRequests")
    void saveTransactionRequest(@RequestBody TransactionRequestBean transactionRequestBean);
}
