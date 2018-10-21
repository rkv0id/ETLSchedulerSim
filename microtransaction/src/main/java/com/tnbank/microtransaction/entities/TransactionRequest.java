package com.tnbank.microtransaction.entities;

import com.tnbank.microtransaction.beans.AccountBean;
import com.tnbank.microtransaction.proxies.MicroaccountProxy;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.*;

@Entity
@Data
@NoArgsConstructor
public class TransactionRequest {

    private @Id
    @Size(min = 15, max = 15) String id;
    private String customerPresentId = "T2XNotNeeded";
    private String beneficiaryId = "NONE";
    private String sourceId = "NONE";
    private String description = "no specific description given";
    private @NotNull @Min(50) Long amount;
    private LocalDateTime timestamp = LocalDateTime.now(Clock.systemUTC());
    private @NotNull @Size(min = 3, max = 3) String typeCode;
    private String frequency = "NONE";
    private LocalDate endTimestamp = LocalDate.now(Clock.systemUTC());
    private int priority = 100;
    private boolean validated = false;
    private String status = "processing";

    public void reassign(MicroaccountProxy microaccountProxy) {
        AccountBean toAccount, fromAccount;
        if (Duration.between(timestamp,LocalDateTime.now(Clock.systemUTC())).getSeconds() > 17 && priority < 1500) {
            switch(typeCode) {
                case "WIT":
                    fromAccount = microaccountProxy.getAccountById(sourceId);
                    switchDEPWIT(fromAccount);
                    break;
                case "DEP":
                    toAccount = microaccountProxy.getAccountById(beneficiaryId);
                    switchDEPWIT(toAccount);
                    break;
                case "T2X":
                    toAccount = microaccountProxy.getAccountById(beneficiaryId);
                    fromAccount = microaccountProxy.getAccountById(sourceId);
                    switchingT2X(fromAccount);
                    switchingT2X(toAccount);
                    break;
            }
        }
    }

    private void switchDEPWIT(AccountBean toAccount) {
        switch (toAccount.getTypeCode()) {
            case "NORM":
                priority += 4;
                break;
            case "NENT":
                priority += 8;
                break;
            case "PREM":
                priority += 6;
                break;
            case "PENT":
                priority += 12;
                break;
        }
    }

    private void switchingT2X(AccountBean toAccount) {
        switch (toAccount.getTypeCode()) {
            case "NORM":
                priority += 2;
                break;
            case "NENT":
                priority += 4;
                break;
            case "PREM":
                priority += 3;
                break;
            case "PENT":
                priority += 6;
                break;
        }
    }

}
