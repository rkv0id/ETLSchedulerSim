package com.tnbank.agentui.ui;

import com.tnbank.agentui.beans.TransactionRequestBean;
import com.tnbank.agentui.proxies.Services;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;

@SpringComponent
public class RequestsLayout extends Grid<TransactionRequestBean> {
    public RequestsLayout() {
        super(TransactionRequestBean.class);
        setSizeFull();
    }

    public void fillItems() {
        setItems(Services.getTransactionProxy().getAllTransactionRequests().getContent());
        setColumns("id", "validated", "customerPresentId", "amount", "beneficiaryId", "sourceId");
    }
}
