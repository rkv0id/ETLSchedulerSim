package com.tnbank.agentui.ui;

import com.tnbank.agentui.beans.TransactionRequestBean;
import com.tnbank.agentui.proxies.Services;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;

import java.util.Comparator;

@SpringComponent
public class RequestsLayout extends Grid<TransactionRequestBean> {
    public RequestsLayout() {
        super(TransactionRequestBean.class);
        setSizeFull();
    }

    void fillItems() {
        setItems(Services.getTransactionProxy().getAllTransactionRequests().getContent().stream().sorted(Comparator.comparing(TransactionRequestBean::getTimestamp).reversed()));
        setColumns("id", "status", "customerPresentId", "amount", "beneficiaryId", "sourceId");
    }
}
