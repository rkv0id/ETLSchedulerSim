package com.tnbank.microtransaction.proxies;

import com.tnbank.microtransaction.beans.DimTransactionBean;
import com.tnbank.microtransaction.beans.DimTransactionRequestBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "zuul-server")
@RibbonClient(name = "microetl")
public interface MicroetlProxy {
    @PostMapping(value = "/microetl/dimTransactions")
    void saveDimTransaction(@RequestBody DimTransactionBean dimTransactionBean);
    @PostMapping(value = "/microetl/dimTransactionRequests")
    void saveDimTransactionRequest(@RequestBody DimTransactionRequestBean dimTransactionRequestBean);
}
