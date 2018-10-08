package com.tnbank.agentui.ui;

import com.vaadin.annotations.Title;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URI;


@SpringUI
@Title(value = "Agency Simulation")
public class IndexUI extends UI {
    private VerticalLayout root;
    private @Autowired DepositLayout depositLayout;
    private @Autowired WithdrawLayout withdrawLayout;
    private @Autowired TxTransferLayout txTransferLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(o -> ((GrantedAuthority) o).toString().equals("ROLE_MANAGER"))) {
            setupLayout();
            addHeader();
            root.addComponent(new Label("Hey Manager"));
        } else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(o -> ((GrantedAuthority) o).toString().equals("ROLE_AGENT"))) {
            setupLayout();
            addHeader();
            addTxMenu();
        }
    }

    private void addTxMenu() {
        HorizontalLayout txHeaderLayout = new HorizontalLayout();
        txHeaderLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        VerticalLayout txLayout = new VerticalLayout();
        txLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        ComboBox<String> txCustomerCB = new ComboBox<>("CUSTOMER PRESENT");
        txCustomerCB.setPlaceholder("Search..");
        txCustomerCB.setItems("Customer1","Customer2","Customer3","Customer4");
        txCustomerCB.setEnabled(false);

        final RadioButtonGroup<String> txRadio = new RadioButtonGroup<>("Specify an operation type".toUpperCase());
        Button resetBtn = new Button("Reset");
        resetBtn.addClickListener(event -> {
            depositLayout.reset();
            withdrawLayout.reset();
            txTransferLayout.reset();
        });
        Button submitBtn = new Button("Submit Operation");
        txRadio.setItems("Deposit","Withdraw","2-tier Transfer");
        txRadio.setStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        txRadio.addValueChangeListener(choiceSelectedEvent -> {
            switch (choiceSelectedEvent.getValue()) {
                case "Deposit":
                    txLayout.removeAllComponents();
                    txCustomerCB.setEnabled(true);
                    txLayout.addComponent(depositLayout);
                    break;
                case "Withdraw":
                    txLayout.removeAllComponents();
                    txCustomerCB.setEnabled(true);
                    txLayout.addComponent(withdrawLayout);
                    break;
                case "2-tier Transfer":
                    txLayout.removeAllComponents();
                    txCustomerCB.setEnabled(false);
                    txLayout.addComponent(txTransferLayout);
                    break;
            }
            HorizontalLayout hl = new HorizontalLayout();
            hl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            hl.addComponents(resetBtn, submitBtn);
            txLayout.addComponent(hl);
        });

        txHeaderLayout.addComponent(txRadio);
        txHeaderLayout.addComponent(txCustomerCB);

        root.addComponents(txHeaderLayout,txLayout);
    }

    private void addHeader() {
        URI currentLoc = Page.getCurrent().getLocation();
        Link iconic = new Link(null, new ExternalResource(currentLoc.toString() + "logout"));
        iconic.setIcon(new ClassResource("/static/images/logo_centered.png"));
        root.addComponents(iconic, new VerticalSplitPanel());
    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(root);
    }
}
