package com.tnbank.agentui.ui;

import com.tnbank.agentui.beans.AccountBean;
import com.tnbank.agentui.beans.TransactionRequestBean;
import com.tnbank.agentui.proxies.Services;
import com.vaadin.shared.Registration;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.vaadin.ui.NumberField;

import java.time.LocalDate;


@SpringComponent
public class TxTransferLayout extends VerticalLayout {
    private TextArea memo = new TextArea("MEMO (OPTIONAL: Maximum of 255 characters)");
    private ComboBox<String> fromAccountCB = new ComboBox<>("FROM ACCOUNT");
    private ComboBox<String> toAccountCB = new ComboBox<>("TO ACCOUNT");
    private NumberField amount = new NumberField("AMOUNT (IN DT)");
    private RadioButtonGroup<String> txRadio = new RadioButtonGroup<>("TRANSFER TYPE");
    private DateField endDateDF = new DateField("END DATE");
    private ComboBox<String> freqCB = new ComboBox<>("FREQUENCY");

    void reset() {
        memo.clear();
        fromAccountCB.clear();
        toAccountCB.clear();
        amount.clear();
        endDateDF.clear();
        freqCB.clear();
    }

    public TxTransferLayout() {
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
        HorizontalLayout hl2 = new HorizontalLayout();
        HorizontalLayout hl3 = new HorizontalLayout();
        hl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        hl2.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        hl3.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        fromAccountCB.setPlaceholder("Search..");
        toAccountCB.setPlaceholder("Search..");
        amount.setPlaceholder("Amount..");

        hl.addComponents(fromAccountCB,toAccountCB);

        txRadio.setItems("One-Time Transfer","Automatic Transfer");
        txRadio.addValueChangeListener(choiceSelectedEvent -> {
            if (choiceSelectedEvent.getValue().equals("Automatic Transfer")) {
                endDateDF.setPlaceholder("Pick an end date");
                freqCB.setPlaceholder("Pick a frequency");
                freqCB.setEmptySelectionAllowed(false);
                freqCB.setItems("Weekly","1st & 15th of each month","Monthly","Every 2 months");
                hl3.addComponents(endDateDF,freqCB);
                removeComponent(memo);
                addComponent(hl3);
                addComponent(memo);
            } else {
                hl3.removeAllComponents();
                removeComponent(hl3);
            }
        });

        hl2.addComponents(txRadio,amount);

        addComponents(hl,hl2);
    }

    void setCBItems() {
        fromAccountCB.setItems(Services.getAccountProxy().getAllAccounts().getContent().stream().map(AccountBean::getId));
        toAccountCB.setItems(Services.getAccountProxy().getAllAccounts().getContent().stream().map(AccountBean::getId));
        fromAccountCB.addValueChangeListener(event -> {
            if (event.getValue() != null)
                toAccountCB.setItems(Services.getAccountProxy().getAllAccounts().getContent().stream().map(AccountBean::getId).filter(s -> !s.equals(event.getValue())));
            else
                toAccountCB.setItems(Services.getAccountProxy().getAllAccounts().getContent().stream().map(AccountBean::getId));
        });
        toAccountCB.addValueChangeListener(event -> {
            if (event.getValue() != null)
                fromAccountCB.setItems(Services.getAccountProxy().getAllAccounts().getContent().stream().map(AccountBean::getId).filter(s -> !s.equals(event.getValue())));
            else
                fromAccountCB.setItems(Services.getAccountProxy().getAllAccounts().getContent().stream().map(AccountBean::getId));
        });
    }

    public Registration assignSubmitBtn(Button submitBtn) {
        return submitBtn.addClickListener(event -> {
            TransactionRequestBean transactionRequestBean = new TransactionRequestBean();
            transactionRequestBean.setAmount(Long.parseLong(amount.getValue()));
            transactionRequestBean.setSourceId(fromAccountCB.getValue());
            transactionRequestBean.setBeneficiaryId(toAccountCB.getValue());
            transactionRequestBean.setDescription(memo.getValue());
            transactionRequestBean.setTypeCode("T2X");
            transactionRequestBean.setFrequency(freqCB.getValue());
            if (freqCB.getValue() != null)
                transactionRequestBean.setEndTimestamp(LocalDate.of(endDateDF.getValue().getYear(),endDateDF.getValue().getMonth(),endDateDF.getValue().getDayOfMonth()));
            transactionRequestBean.assignPriority();
            transactionRequestBean.assignId();
            Services.getTransactionProxy().saveTransactionRequest(transactionRequestBean);
            reset();
        });
    }
}
