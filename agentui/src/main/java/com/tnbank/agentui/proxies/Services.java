package com.tnbank.agentui.proxies;

import com.vaadin.spring.server.SpringVaadinServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

@Service
public class Services {

    public static MicrocustomerProxy getCustomerProxy() {
        return getApplicationContext().getBean(MicrocustomerProxy.class);
    }

    public static MicroaccountProxy getAccountProxy() {
        return getApplicationContext().getBean(MicroaccountProxy.class);
    }

    public static MicrotransactionProxy getTransactionProxy() {
        return getApplicationContext().getBean(MicrotransactionProxy.class);
    }

    public static MicroetlProxy getEtlProxy() {
        return getApplicationContext().getBean(MicroetlProxy.class);
    }

    private static ApplicationContext getApplicationContext() {
        ServletContext servletContext = SpringVaadinServlet.getCurrent().getServletContext();
        return WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

}
