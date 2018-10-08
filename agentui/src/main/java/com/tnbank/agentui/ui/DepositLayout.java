package com.tnbank.agentui.ui;

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
        toAccountCB.setItems("Account1","Account2","Account3","Account4");

        amount.setPlaceholder("Amount..");

        hl.addComponent(toAccountCB);
        hl.addComponent(amount);
        addComponent(hl);
    }
}
