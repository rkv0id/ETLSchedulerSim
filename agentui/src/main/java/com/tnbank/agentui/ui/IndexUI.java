package com.tnbank.agentui.ui;

import com.tnbank.agentui.proxies.Services;
import com.vaadin.annotations.Title;
import com.vaadin.server.*;
import com.vaadin.shared.Registration;
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
    private @Autowired
    DepositLayout depositLayout;
    private @Autowired
    WithdrawLayout withdrawLayout;
    private @Autowired
    TxTransferLayout txTransferLayout;
    private @Autowired
    RequestsLayout requestsLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setupLayout();
        addHeader();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(o -> o.toString().equals("ROLE_MANAGER"))) {
            root.addComponent(new Label("Hey Manager"));
        } else if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(o -> o.toString().equals("ROLE_AGENT"))) {
            addTxMenu();
        }
        addFooter();
    }

    private void addFooter() {
        root.addComponents();
    }

    private void addTxMenu() {
        HorizontalLayout txHeaderLayout = new HorizontalLayout();
        txHeaderLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        VerticalLayout txLayout = new VerticalLayout();
        txLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        ComboBox<String> txCustomerCB = new ComboBox<>("CUSTOMER PRESENT");
        txCustomerCB.setPlaceholder("Search..");
        txCustomerCB.setItems(Services.getCustomerProxy()
                .getAllCustomers()
                .getContent()
                .stream()
                .map(customerBean -> customerBean.getLastName() + " " + customerBean.getFirstName() + " - " + customerBean.getId()));
        txCustomerCB.setEnabled(false);

        final RadioButtonGroup<String> txRadio = new RadioButtonGroup<>("Specify an operation type".toUpperCase());
        Button resetBtn = new Button("Reset");
        resetBtn.addClickListener(event -> {
            depositLayout.reset();
            withdrawLayout.reset();
            txTransferLayout.reset();
        });
        Button submitBtn = new Button("Submit Operation");
        final Registration[] submitListener = new Registration[1];
        txRadio.setItems("Deposit", "Withdraw", "2-tier Transfer");
        txRadio.setStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        txRadio.addValueChangeListener(choiceSelectedEvent -> {
            switch (choiceSelectedEvent.getValue()) {
                case "Deposit":
                    txLayout.removeAllComponents();
                    txCustomerCB.setEnabled(true);
                    txLayout.addComponent(depositLayout);
                    txCustomerCB.addValueChangeListener(event -> {
                        if (! (event.getValue() == null)) {
                            depositLayout.setToAccountCBItems(event.getValue().substring(event.getValue().length() - 8));
                            if (!(submitListener[0] == null)) submitListener[0].remove();
                            submitListener[0] = depositLayout.assignSubmitBtn(submitBtn,event.getValue().substring(event.getValue().length() - 8));
                        } else depositLayout.setToAccountCBItems("");
                    });
                    break;
                case "Withdraw":
                    txLayout.removeAllComponents();
                    txCustomerCB.setEnabled(true);
                    txLayout.addComponent(withdrawLayout);
                    txCustomerCB.addValueChangeListener(event -> {
                        if (!(event.getValue() == null)) {
                            withdrawLayout.setToAccountCBItems(event.getValue().substring(event.getValue().length() - 8));
                            if (!(submitListener[0] == null)) submitListener[0].remove();
                            submitListener[0] = withdrawLayout.assignSubmitBtn(submitBtn,event.getValue().substring(event.getValue().length() - 8));
                        } else withdrawLayout.setToAccountCBItems("");
                    });
                    break;
                case "2-tier Transfer":
                    txLayout.removeAllComponents();
                    txCustomerCB.setEnabled(false);
                    txLayout.addComponent(txTransferLayout);
                    txTransferLayout.setCBItems();
                    if (!(submitListener[0] == null)) submitListener[0].remove();
                    submitListener[0] = txTransferLayout.assignSubmitBtn(submitBtn);
                    break;
            }
            HorizontalLayout hl = new HorizontalLayout();
            hl.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

            hl.addComponents(resetBtn, submitBtn);
            txLayout.addComponent(hl);
        });

        txHeaderLayout.addComponent(txRadio);
        txHeaderLayout.addComponent(txCustomerCB);

        root.addComponents(txHeaderLayout, txLayout);
    }

    private void addHeader() {
        URI currentLoc = Page.getCurrent().getLocation();
        Link iconic = new Link(null, new ExternalResource(currentLoc.toString() + "logout"));
        iconic.setIcon(new ClassResource("/static/images/logo_centered.png"));
        iconic.setDescription("Logout from Session");
        root.addComponents(iconic, new VerticalSplitPanel());
        Button txBtn = new Button("Trace Transactions");
        txBtn.addClickListener(event -> {
            requestsLayout.fillItems();
            Window requests = new Window("Transfer requests", requestsLayout);
            requests.setWidth("950px");
            requests.setHeight("420px");
            getUI().addWindow(requests);
        });
    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setContent(root);
    }
}
