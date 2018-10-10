package com.tnbank.agentui.ui;

import com.tnbank.agentui.beans.AccountBean;
import com.tnbank.agentui.proxies.Services;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.vaadin.ui.NumberField;

@SpringComponent
public class DepositLayout extends VerticalLayout {
    private TextArea memo = new TextArea("MEMO (OPTIONAL: Maximum of 255 characters)");
    private ComboBox<String> toAccountCB = new ComboBox<>("TO ACCOUNT");
    private NumberField amount = new NumberField("AMOUNT (IN DT)");

    public void reset() {
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

        amount.setPlaceholder("Amount..");

        hl.addComponent(toAccountCB);
        hl.addComponent(amount);
        addComponent(hl);
    }

    public void setToAccountCBItems(String cin) {
        if (!cin.equals(""))
            toAccountCB.setItems(Services.getAccountProxy().getAccountsByCustomer(cin).getContent().stream().map(AccountBean::getId));
        else
            toAccountCB.setItems("");
    }
}
