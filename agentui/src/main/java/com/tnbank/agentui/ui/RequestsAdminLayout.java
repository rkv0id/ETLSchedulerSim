package com.tnbank.agentui.ui;

import com.tnbank.agentui.beans.TransactionRequestBean;
import com.tnbank.agentui.proxies.Services;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;

import java.util.Comparator;

@SpringComponent
public class RequestsAdminLayout extends Grid<TransactionRequestBean> {

    public RequestsAdminLayout() {
        super(TransactionRequestBean.class);
        setSizeFull();
    }

    void fillItems() {
        setItems(Services.getTransactionProxy().getAllTransactionRequests().getContent().stream()
                .sorted(Comparator.comparing(TransactionRequestBean::getPriority).reversed())
                .filter(transactionRequestBean -> transactionRequestBean.getStatus().equals("processing"))
        );
        setColumns("id", "status", "customerPresentId", "amount", "beneficiaryId", "sourceId");
    }
}
