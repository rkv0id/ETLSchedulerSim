package com.tnbank.microaccount.proxies;

import com.tnbank.microaccount.beans.DimAccountBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@FeignClient(name = "microetl", url = "localhost:9004")
public interface MicroetlProxy {
    @RequestMapping(method = RequestMethod.POST, path = "/dimAccounts")
    void saveDimAccount(@RequestBody DimAccountBean dimAccountBean);
}
