package com.tnbank.agentui.ui;

import com.tnbank.agentui.beans.AccountBean;
import com.tnbank.agentui.beans.TransactionRequestBean;
import com.tnbank.agentui.proxies.Services;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.vaadin.ui.NumberField;

@SpringComponent
public class DepositLayout extends VerticalLayout {
    private TextArea memo = new TextArea("MEMO (OPTIONAL: Maximum of 255 characters)");
    private ComboBox<String> toAccountCB = new ComboBox<>("TO ACCOUNT");
    private NumberField amount = new NumberField("AMOUNT (IN DT)");

    void reset() {
        memo.clear();
        toAccountCB.clear();
        amount.clear();
    }

    public DepositLayout() {
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        addDetailsBtn();
        addMemoTF();
    }

    private void addMemoTF() {
        memo.setWidth("380px");
        memo.setPlaceholder("Transfer's description..");
        addComponent(memo);
    }

    private void addDetailsBtn() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        toAccountCB.setPlaceholder("Search..");
        toAccountCB.setEmptySelectionAllowed(false);

        amount.setPlaceholder("Amount..");
        amount.setMinValue(1);
        amount.setNegativeAllowed(false);

        hl.addComponent(toAccountCB);
        hl.addComponent(amount);
        addComponent(hl);
    }

    void setToAccountCBItems(String cin) {
        toAccountCB.setValue("");
        if (!cin.equals(""))
            toAccountCB.setItems(Services.getAccountProxy().getAccountsByCustomer(cin).getContent().stream().map(AccountBean::getId));
        else
            toAccountCB.setItems("");
    }

    Registration assignSubmitBtn(Button submitBtn, String customerCin) {
        return submitBtn.addClickListener(event -> {
            TransactionRequestBean transactionRequestBean = new TransactionRequestBean();
            transactionRequestBean.setAmount(Long.parseLong(amount.getValue()));
            transactionRequestBean.setBeneficiaryId(toAccountCB.getValue());
            transactionRequestBean.setCustomerPresentId(customerCin);
            if (!memo.getValue().equals(""))
                transactionRequestBean.setDescription(memo.getValue());
            transactionRequestBean.setTypeCode("DEP");
            transactionRequestBean.assignPriority();
            transactionRequestBean.assignId();
            Services.getTransactionProxy().saveTransactionRequest(transactionRequestBean);
            reset();
        });
    }
}
