package com.tnbank.agentui.proxies;

import com.tnbank.agentui.beans.TransactionRequestBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "microtransaction", url = "localhost:9003")
public interface MicrotransactionProxy {
    @RequestMapping(method = RequestMethod.GET, path = "/transactionRequests")
    Resources<TransactionRequestBean> getAllTransactionRequests();
    @RequestMapping(method = RequestMethod.POST, path = "/transactionRequests")
    void saveTransactionRequest(@RequestBody TransactionRequestBean transactionRequestBean);
}
