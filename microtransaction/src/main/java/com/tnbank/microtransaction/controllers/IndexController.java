package com.tnbank.microtransaction.controllers;

import com.tnbank.microtransaction.beans.AccountBean;
import com.tnbank.microtransaction.beans.DimTransactionBean;
import com.tnbank.microtransaction.beans.DimTransactionRequestBean;
import com.tnbank.microtransaction.entities.Transaction;
import com.tnbank.microtransaction.entities.TransactionRequest;
import com.tnbank.microtransaction.proxies.MicroaccountProxy;
import com.tnbank.microtransaction.proxies.MicroetlProxy;
import com.tnbank.microtransaction.repositories.TransactionRepository;
import com.tnbank.microtransaction.repositories.TransactionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class IndexController {
    private TransactionRequestRepository transactionRequestRepository;
    private TransactionRepository transactionRepository;
    private MicroaccountProxy microaccountProxy;
    private MicroetlProxy microetlProxy;

    public @Autowired
    void setMicroetlProxy(MicroetlProxy microetlProxy) {
        this.microetlProxy = microetlProxy;
    }

    public @Autowired
    void setTransactionRequestRepository(TransactionRequestRepository transactionRequestRepository) {
        this.transactionRequestRepository = transactionRequestRepository;
    }

    public @Autowired
    void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public @Autowired
    void setMicroaccountProxy(MicroaccountProxy microaccountProxy) {
        this.microaccountProxy = microaccountProxy;
    }

    @ResponseBody
    @RequestMapping(value = "/transactionRequests", method = RequestMethod.POST)
    public void save(@RequestBody TransactionRequest transactionRequest) {
        transactionRequestRepository.save(transactionRequest);
        AccountBean fromAccount, toAccount;
        Transaction transaction; DimTransactionBean dimTransactionBean;
        DimTransactionRequestBean dimTransactionRequestBean = new DimTransactionRequestBean();
        dimTransactionRequestBean.setAmount(transactionRequest.getAmount());
        dimTransactionRequestBean.setId(transactionRequest.getId());
        dimTransactionRequestBean.setTimestamp(transactionRequest.getTimestamp());
        dimTransactionRequestBean.setTypeCode(transactionRequest.getTypeCode());
        microetlProxy.saveDimTransactionRequest(dimTransactionRequestBean);
        if (!transactionRequest.getStatus().equals("processing")) {
            switch (transactionRequest.getTypeCode()) {
                case "WIT":
                    fromAccount = microaccountProxy.getAccountById(transactionRequest.getSourceId());
                    fromAccount.setBalance(fromAccount.getBalance() - transactionRequest.getAmount());
                    microaccountProxy.saveAccount(fromAccount);
                    transaction = new Transaction();
                    transaction.setId(transactionRequest.getId().substring(0, 12));
                    transaction.setAmount(transactionRequest.getAmount());
                    transaction.setCustomerPresentId(transactionRequest.getCustomerPresentId());
                    transaction.setSourceId(transactionRequest.getSourceId());
                    transaction.setDescription(transactionRequest.getDescription());
                    transaction.setTimestamp(transactionRequest.getTimestamp());
                    transaction.setTypeCode("WIT");
                    break;
                case "T2X":
                    fromAccount = microaccountProxy.getAccountById(transactionRequest.getSourceId());
                    toAccount = microaccountProxy.getAccountById(transactionRequest.getBeneficiaryId());
                    fromAccount.setBalance(fromAccount.getBalance() - transactionRequest.getAmount());
                    toAccount.setBalance(toAccount.getBalance() + transactionRequest.getAmount());
                    microaccountProxy.saveAccount(fromAccount);
                    microaccountProxy.saveAccount(toAccount);
                    transaction = new Transaction();
                    transaction.setId(transactionRequest.getId().substring(0, 12));
                    transaction.setAmount(transactionRequest.getAmount());
                    transaction.setCustomerPresentId(transactionRequest.getCustomerPresentId());
                    transaction.setSourceId(transactionRequest.getSourceId());
                    transaction.setBeneficiaryId(transactionRequest.getBeneficiaryId());
                    transaction.setDescription(transactionRequest.getDescription());
                    transaction.setTimestamp(transactionRequest.getTimestamp());
                    transaction.setEndTimestamp(transactionRequest.getEndTimestamp());
                    transaction.setFrequency(transactionRequest.getFrequency());
                    transaction.setTypeCode("T2X");
                    break;
                default:
                    toAccount = microaccountProxy.getAccountById(transactionRequest.getBeneficiaryId());
                    toAccount.setBalance(toAccount.getBalance() + transactionRequest.getAmount());
                    microaccountProxy.saveAccount(toAccount);
                    transaction = new Transaction();
                    transaction.setId(transactionRequest.getId().substring(0, 12));
                    transaction.setAmount(transactionRequest.getAmount());
                    transaction.setCustomerPresentId(transactionRequest.getCustomerPresentId());
                    transaction.setBeneficiaryId(transactionRequest.getBeneficiaryId());
                    transaction.setDescription(transactionRequest.getDescription());
                    transaction.setTimestamp(transactionRequest.getTimestamp());
                    transaction.setTypeCode("DEP");
                    break;
            }
            transactionRepository.save(transaction);
            dimTransactionBean = new DimTransactionBean();
            dimTransactionBean.setId(transaction.getId());
            dimTransactionBean.setAmount(transaction.getAmount());
            dimTransactionBean.setTimestamp(transaction.getTimestamp());
            dimTransactionBean.setTypeCode(transaction.getTypeCode());
            microetlProxy.saveDimTransaction(dimTransactionBean);
            transactionRequestRepository.save(transactionRequest);
            dimTransactionRequestBean.setAmount(transactionRequest.getAmount());
            dimTransactionRequestBean.setId(transactionRequest.getId());
            dimTransactionRequestBean.setTimestamp(transactionRequest.getTimestamp());
            dimTransactionRequestBean.setTypeCode(transactionRequest.getTypeCode());
            microetlProxy.saveDimTransactionRequest(dimTransactionRequestBean);
        } else if (transactionRequest.getAmount() < 3000) {
            switch (transactionRequest.getTypeCode()) {
                case "WIT":
                    fromAccount = microaccountProxy.getAccountById(transactionRequest.getSourceId());
                    transactionRequest.setValidated(
                            fromAccount.getBalance() > transactionRequest.getAmount()
                                    && fromAccount.getStatusCode().equals("OPN")
                    );
                    if (transactionRequest.isValidated()) {
                        fromAccount.setBalance(fromAccount.getBalance() - transactionRequest.getAmount());
                        microaccountProxy.saveAccount(fromAccount);
                        transaction = new Transaction();
                        transaction.setId(transactionRequest.getId().substring(0, 12));
                        transaction.setAmount(transactionRequest.getAmount());
                        transaction.setCustomerPresentId(transactionRequest.getCustomerPresentId());
                        transaction.setSourceId(transactionRequest.getSourceId());
                        transaction.setDescription(transactionRequest.getDescription());
                        transaction.setTimestamp(transactionRequest.getTimestamp());
                        transaction.setTypeCode("WIT");
                        transactionRepository.save(transaction);
                        dimTransactionBean = new DimTransactionBean();
                        dimTransactionBean.setId(transaction.getId());
                        dimTransactionBean.setAmount(transaction.getAmount());
                        dimTransactionBean.setTimestamp(transaction.getTimestamp());
                        dimTransactionBean.setTypeCode(transaction.getTypeCode());
                        microetlProxy.saveDimTransaction(dimTransactionBean);
                        transactionRequest.setStatus("Validated");
                    } else {
                        transactionRequest.setStatus("Refused");
                    }
                    break;
                case "T2X":
                    fromAccount = microaccountProxy.getAccountById(transactionRequest.getSourceId());
                    toAccount = microaccountProxy.getAccountById(transactionRequest.getBeneficiaryId());
                    transactionRequest.setValidated(
                            fromAccount.getBalance() > transactionRequest.getAmount()
                                    && fromAccount.getStatusCode().equals("OPN")
                                    && toAccount.getStatusCode().equals("OPN")
                    );
                    if (transactionRequest.isValidated()) {
                        fromAccount.setBalance(fromAccount.getBalance() - transactionRequest.getAmount());
                        toAccount.setBalance(toAccount.getBalance() + transactionRequest.getAmount());
                        microaccountProxy.saveAccount(fromAccount);
                        microaccountProxy.saveAccount(toAccount);
                        transaction = new Transaction();
                        transaction.setId(transactionRequest.getId().substring(0, 12));
                        transaction.setAmount(transactionRequest.getAmount());
                        transaction.setCustomerPresentId(transactionRequest.getCustomerPresentId());
                        transaction.setSourceId(transactionRequest.getSourceId());
                        transaction.setBeneficiaryId(transactionRequest.getBeneficiaryId());
                        transaction.setDescription(transactionRequest.getDescription());
                        transaction.setTimestamp(transactionRequest.getTimestamp());
                        transaction.setEndTimestamp(transactionRequest.getEndTimestamp());
                        transaction.setFrequency(transactionRequest.getFrequency());
                        transaction.setTypeCode("T2X");
                        transactionRepository.save(transaction);
                        dimTransactionBean = new DimTransactionBean();
                        dimTransactionBean.setId(transaction.getId());
                        dimTransactionBean.setAmount(transaction.getAmount());
                        dimTransactionBean.setTimestamp(transaction.getTimestamp());
                        dimTransactionBean.setTypeCode(transaction.getTypeCode());
                        microetlProxy.saveDimTransaction(dimTransactionBean);
                        transactionRequest.setStatus("Validated");
                    } else {
                        transactionRequest.setStatus("Refused");
                    }
                    break;
                default:
                    toAccount = microaccountProxy.getAccountById(transactionRequest.getBeneficiaryId());
                    toAccount.setBalance(toAccount.getBalance() + transactionRequest.getAmount());
                    microaccountProxy.saveAccount(toAccount);
                    transaction = new Transaction();
                    transaction.setId(transactionRequest.getId().substring(0, 12));
                    transaction.setAmount(transactionRequest.getAmount());
                    transaction.setCustomerPresentId(transactionRequest.getCustomerPresentId());
                    transaction.setBeneficiaryId(transactionRequest.getBeneficiaryId());
                    transaction.setDescription(transactionRequest.getDescription());
                    transaction.setTimestamp(transactionRequest.getTimestamp());
                    transaction.setTypeCode("DEP");
                    transactionRepository.save(transaction);
                    dimTransactionBean = new DimTransactionBean();
                    dimTransactionBean.setId(transaction.getId());
                    dimTransactionBean.setAmount(transaction.getAmount());
                    dimTransactionBean.setTimestamp(transaction.getTimestamp());
                    dimTransactionBean.setTypeCode(transaction.getTypeCode());
                    microetlProxy.saveDimTransaction(dimTransactionBean);
                    transactionRequest.setValidated(true);
                    transactionRequest.setStatus("Validated");
            }
            transactionRequestRepository.save(transactionRequest);
            dimTransactionRequestBean.setAmount(transactionRequest.getAmount());
            dimTransactionRequestBean.setId(transactionRequest.getId());
            dimTransactionRequestBean.setTimestamp(transactionRequest.getTimestamp());
            dimTransactionRequestBean.setTypeCode(transactionRequest.getTypeCode());
            microetlProxy.saveDimTransactionRequest(dimTransactionRequestBean);
        }
    }
}
