package com.tnbank.microaccount.controllers;

import com.tnbank.microaccount.beans.DimAccountBean;
import com.tnbank.microaccount.entities.Account;
import com.tnbank.microaccount.proxies.MicroetlProxy;
import com.tnbank.microaccount.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestController
public class IndexController implements HealthIndicator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
        logger.info("New account saved @ " + LocalDateTime.now(Clock.systemUTC()));
    }

    @Override
    public Health health() {
        boolean accountBoolean = accountRepository.findAll().iterator().hasNext();
        return accountBoolean ? Health.up().build() : Health.down().build();
    }
}
