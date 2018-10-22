package com.tnbank.microaccount.proxies;

import com.tnbank.microaccount.beans.DimAccountBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "zuul-server")
@RibbonClient(name = "microetl")
public interface MicroetlProxy {
    @PostMapping(value = "/microetl/dimAccounts")
    void saveDimAccount(@RequestBody DimAccountBean dimAccountBean);
}
