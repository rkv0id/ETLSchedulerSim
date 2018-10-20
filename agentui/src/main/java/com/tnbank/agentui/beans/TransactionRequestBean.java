package com.tnbank.agentui.beans;

import com.tnbank.agentui.proxies.Services;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionRequestBean {

    private String id;
    private String customerPresentId = "T2XNotNeeded";
    private String beneficiaryId = "NONE";
    private String sourceId = "NONE";
    private String description = "no specific description given";
    private Long amount;
    private LocalDateTime timestamp = LocalDateTime.now(Clock.systemUTC());
    private String typeCode;
    private String frequency = "NONE";
    private LocalDate endTimestamp = LocalDate.now(Clock.systemUTC());
    private int priority = 100;
    private boolean validated = false;
    private String status = "processing";

    private void priorityAccount(AccountBean toAccount) {
        if (toAccount.getStatusCode().equals("OPN"))
            switch (toAccount.getTypeCode()) {
                case "NORM":
                    priority -= 10;
                    break;
                case "NENT":
                    priority -= 5;
                    break;
                case "PREM":
                    priority += 5;
                    break;
                case "PENT":
                    priority += 10;
                    break;
            }
    }

    public void assignId() {
        id = typeCode + String.valueOf(LocalDateTime.now(Clock.systemUTC()).hashCode()).substring(String.valueOf(LocalDateTime.now(Clock.systemUTC()).hashCode()).length()-9) + "REQ";
    }

    public void assignPriority() {
        AccountBean toAccount,fromAccount;
        switch (typeCode) {
            case "DEP":
                toAccount = Services.getAccountProxy().getAccountById(beneficiaryId).getContent();
                priorityAccount(toAccount);
                priority += 10;
                if (amount > 3000)
                    priority += 10;
                break;
            case "WIT":
                fromAccount = Services.getAccountProxy().getAccountById(sourceId).getContent();
                priorityAccount(fromAccount);
                priority -= 10;
                if (amount > 3000)
                    priority += 5;
                break;
            case "T2X":
                toAccount = Services.getAccountProxy().getAccountById(beneficiaryId).getContent();
                fromAccount = Services.getAccountProxy().getAccountById(sourceId).getContent();
                priorityAccount(toAccount);
                priorityAccount(fromAccount);
                priority += 5;
                if (!LocalDate.of(timestamp.getYear(),timestamp.getMonth(),timestamp.getDayOfMonth()).equals(endTimestamp))
                    priority -= 30;
                break;
        }
    }

}
