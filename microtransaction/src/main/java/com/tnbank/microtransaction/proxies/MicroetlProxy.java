package com.tnbank.microtransaction.proxies;

import com.tnbank.microtransaction.beans.DimTransactionBean;
import com.tnbank.microtransaction.beans.DimTransactionRequestBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@FeignClient(name = "microetl", url = "localhost:9004")
public interface MicroetlProxy {
    @RequestMapping(method = RequestMethod.POST, path = "/dimTransactions")
    void saveDimTransaction(@RequestBody DimTransactionBean dimTransactionBean);
    @RequestMapping(method = RequestMethod.POST, path = "/dimTransactionRequests")
    void saveDimTransactionRequest(@RequestBody DimTransactionRequestBean dimTransactionRequestBean);
}
