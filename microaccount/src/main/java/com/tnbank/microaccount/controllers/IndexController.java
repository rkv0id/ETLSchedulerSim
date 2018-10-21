package com.tnbank.microaccount.controllers;

import com.tnbank.microaccount.beans.DimAccountBean;
import com.tnbank.microaccount.entities.Account;
import com.tnbank.microaccount.proxies.MicroetlProxy;
import com.tnbank.microaccount.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class IndexController {
    private MicroetlProxy microetlProxy;
    private AccountRepository accountRepository;

    public @Autowired
    void setMicroetlProxy(MicroetlProxy microetlProxy) {
        this.microetlProxy = microetlProxy;
    }
    public @Autowired
    void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @ResponseBody
    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public void save(@RequestBody Account account) {
        accountRepository.save(account);
        DimAccountBean dimAccountBean = new DimAccountBean();
        dimAccountBean.setBalance(account.getBalance());
        dimAccountBean.setBeginTimestamp(account.getBeginTimestamp());
        dimAccountBean.setCinCustomer(account.getCinCustomer());
        dimAccountBean.setId(account.getId());
        dimAccountBean.setTypeCode(account.getTypeCode());
        microetlProxy.saveDimAccount(dimAccountBean);
    }
}
