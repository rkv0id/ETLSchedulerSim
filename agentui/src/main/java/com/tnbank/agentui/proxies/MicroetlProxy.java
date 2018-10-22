package com.tnbank.agentui.proxies;

import com.tnbank.agentui.beans.AccountTypeMeasureBean;
import com.tnbank.agentui.beans.RequestDayMeasureBean;
import com.tnbank.agentui.beans.TransactionDayMeasureBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microetl")
public interface MicroetlProxy {
    @GetMapping(value = "/microetl/accountTypeMeasures")
    Resources<AccountTypeMeasureBean> findAllAccountMeasures();
    @GetMapping(value = "/microetl/requestDayMeasures")
    Resources<RequestDayMeasureBean> findAllRequestMeasures();
    @GetMapping(value = "/microetl/transactionDayMeasures")
    Resources<TransactionDayMeasureBean> findAllTransactionMeasures();
}
