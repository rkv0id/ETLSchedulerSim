package com.tnbank.agentui.ui;

import com.tnbank.agentui.beans.AccountBean;
import com.tnbank.agentui.beans.TransactionRequestBean;
import com.tnbank.agentui.proxies.Services;
import com.vaadin.ui.*;

class RequestLayout extends HorizontalLayout {

    RequestLayout(TransactionRequestBean transactionRequestBean, RequestsAdminLayout requestsAdminLayout) {
        DateTimeField timestampField = new DateTimeField("TIMESTAMP", transactionRequestBean.getTimestamp());
        timestampField.setWidth("381px");
        timestampField.setEnabled(false);
        TextField typeField = new TextField("TX TYPE", transactionRequestBean.getTypeCode());
        typeField.setEnabled(false);
        TextField amountField = new TextField("AMOUNT", transactionRequestBean.getAmount().toString());
        amountField.setEnabled(false);
        TextField freqField = new TextField("FREQUENCY",
                transactionRequestBean.getFrequency().equals("NONE") ? transactionRequestBean.getFrequency() :
                        transactionRequestBean.getFrequency() + " to " + transactionRequestBean.getEndTimestamp().toString());
        freqField.setWidth("381px");
        freqField.setEnabled(false);
        TextArea descriptionArea = new TextArea("DESCRIPTION", transactionRequestBean.getDescription());
        descriptionArea.setWidth("381px");
        descriptionArea.setEnabled(false);
        Button validateSwitch = new Button("Validate");
        validateSwitch.setEnabled(transactionRequestBean.getStatus().equals("processing"));
        validateSwitch.addClickListener(event -> {
            transactionRequestBean.setValidated(validateSwitch.getCaption().equals("Validate"));
            validateSwitch.setCaption(validateSwitch.getCaption().equals("Validate") ? "Disprove" : "Validate");
        });
        Button classifyBtn = new Button("Classify");
        classifyBtn.setEnabled(transactionRequestBean.getStatus().equals("processing"));
        classifyBtn.addClickListener(event -> {
            transactionRequestBean.setStatus(transactionRequestBean.isValidated() ? "Validated" : "Refused");
            Services.getTransactionProxy().saveTransactionRequest(transactionRequestBean);
            validateSwitch.setEnabled(false);
            classifyBtn.setEnabled(false);
            classifyBtn.setCaption("Classified");
            requestsAdminLayout.fillItems();
        });

        VerticalLayout txLayout = new VerticalLayout(); txLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        txLayout.addComponents(
                timestampField,
                new HorizontalLayout(typeField, amountField),
                freqField, descriptionArea,
                new HorizontalLayout(validateSwitch, classifyBtn)
        );

        VerticalLayout toAccountLayout = new VerticalLayout(); toAccountLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        VerticalLayout fromAccountLayout = new VerticalLayout(); fromAccountLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        VerticalLayout accountsLayout = new VerticalLayout(); accountsLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        AccountBean toAccount,fromAccount; TextField customerField, toAccountBalanceField, fromAccountBalanceField, toAccountTypeField, fromAccountTypeField;
        switch (transactionRequestBean.getTypeCode()) {
            case "T2X":
                toAccount = Services.getAccountProxy().getAccountById(transactionRequestBean.getBeneficiaryId()).getContent();
                toAccountBalanceField = new TextField(
                        "BALANCE",
                        toAccount.getBalance().toString() + " DT"
                );
                toAccountBalanceField.setEnabled(false);
                toAccountTypeField = new TextField(
                        "ACCOUNT TYPE",
                        toAccount.getTypeCode()
                );
                toAccountTypeField.setEnabled(false);
                toAccountLayout.addComponents(
                        new Label("Beneficiary Account n°" + transactionRequestBean.getBeneficiaryId()),
                        new HorizontalLayout(toAccountBalanceField, toAccountTypeField)
                );

                fromAccount = Services.getAccountProxy().getAccountById(transactionRequestBean.getSourceId()).getContent();
                fromAccountBalanceField = new TextField(
                        "BALANCE",
                        fromAccount.getBalance().toString() + " DT"
                );
                fromAccountBalanceField.setEnabled(false);
                fromAccountTypeField = new TextField(
                        "ACCOUNT TYPE",
                        fromAccount.getTypeCode()
                );
                fromAccountTypeField.setEnabled(false);
                fromAccountLayout.addComponents(
                        new Label("Source Account n°" + transactionRequestBean.getSourceId()),
                        new HorizontalLayout(fromAccountBalanceField, fromAccountTypeField)
                );

                accountsLayout.addComponents(fromAccountLayout, toAccountLayout);
                break;
            case "DEP":
                toAccount = Services.getAccountProxy().getAccountById(transactionRequestBean.getBeneficiaryId()).getContent();
                customerField = new TextField(
                        "PRESENTED CUSTOMER",
                        Services.getCustomerProxy().getCustomerById(toAccount.getCinCustomer()).getContent().getFirstName()
                                + " "
                                + Services.getCustomerProxy().getCustomerById(toAccount.getCinCustomer()).getContent().getLastName()
                );
                customerField.setEnabled(false);
                toAccountBalanceField = new TextField(
                        "BALANCE",
                        toAccount.getBalance().toString() + " DT"
                );
                toAccountBalanceField.setEnabled(false);
                toAccountTypeField = new TextField(
                        "ACCOUNT TYPE",
                        toAccount.getTypeCode()
                );
                toAccountTypeField.setEnabled(false);
                toAccountLayout.addComponents(
                        new Label("Beneficiary Account n°" + transactionRequestBean.getBeneficiaryId()),
                        customerField,
                        new HorizontalLayout(toAccountBalanceField, toAccountTypeField)
                );
                accountsLayout.addComponent(toAccountLayout);
                break;
            case "WIT":
                fromAccount = Services.getAccountProxy().getAccountById(transactionRequestBean.getSourceId()).getContent();
                customerField = new TextField(
                        "PRESENTED CUSTOMER",
                        Services.getCustomerProxy().getCustomerById(fromAccount.getCinCustomer()).getContent().getFirstName()
                                + " "
                                + Services.getCustomerProxy().getCustomerById(fromAccount.getCinCustomer()).getContent().getLastName()
                );
                customerField.setEnabled(false);
                fromAccountBalanceField = new TextField(
                        "BALANCE",
                        fromAccount.getBalance().toString() + " DT"
                );
                fromAccountBalanceField.setEnabled(false);
                fromAccountTypeField = new TextField(
                        "ACCOUNT TYPE",
                        fromAccount.getTypeCode()
                );
                fromAccountTypeField.setEnabled(false);
                fromAccountLayout.addComponents(
                        new Label("Source Account n°" + transactionRequestBean.getSourceId()),
                        customerField,
                        new HorizontalLayout(fromAccountBalanceField, fromAccountTypeField)
                );
                accountsLayout.addComponent(fromAccountLayout);
                break;
        }

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(accountsLayout, txLayout);
    }
}
