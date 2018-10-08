package com.tnbank.agentui.ui;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.vaadin.ui.NumberField;

@SpringComponent
public class WithdrawLayout extends VerticalLayout {
    private TextArea memo = new TextArea("MEMO (OPTIONAL: Maximum of 255 characters)");
    private ComboBox<String> fromAccountCB = new ComboBox<>("FROM ACCOUNT");
    private NumberField amount = new NumberField("AMOUNT (IN DT)");

    public void reset() {
        memo.clear();
        fromAccountCB.clear();
        amount.clear();
    }

    public WithdrawLayout() {
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

        fromAccountCB.setPlaceholder("Search..");
        fromAccountCB.setItems("Account1","Account2","Account3","Account4");

        amount.setPlaceholder("Amount..");

        hl.addComponent(fromAccountCB);
        hl.addComponent(amount);
        addComponent(hl);
    }
}
