package com.tnbank.microtransaction.controllers;

import com.tnbank.microtransaction.entities.TransactionRequest;
import com.tnbank.microtransaction.proxies.MicroaccountProxy;
import com.tnbank.microtransaction.repositories.TransactionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduledAssignPriority {
    private TransactionRequestRepository transactionRequestRepository;
    private List<TransactionRequest> transactionRequestList;
    private MicroaccountProxy microaccountProxy;

    public @Autowired void setMicroaccountProxy(MicroaccountProxy microaccountProxy) {
        this.microaccountProxy = microaccountProxy;
    }

    public @Autowired void setTransactionRequestRepository(TransactionRequestRepository transactionRequestRepository) {
        this.transactionRequestRepository = transactionRequestRepository;
    }

    public ScheduledAssignPriority() {
        transactionRequestList = new ArrayList<>();
    }

    @Scheduled(fixedDelay = 17000, initialDelay = 10000)
    public void recheck(){
        try {
            transactionRequestList = transactionRequestRepository.findAllByStatusEquals("processing");
        } catch(NullPointerException ex) {
            System.err.println(ex);
        }
        if (!transactionRequestList.isEmpty()) {
            transactionRequestList.forEach(transactionRequest -> {
                transactionRequest.reassign(microaccountProxy);
                transactionRequestRepository.save(transactionRequest);
            });
        } else {
            System.err.println("No Transaction Request to reassign priorities to");
        }
    }
}
