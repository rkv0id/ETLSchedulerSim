package com.tnbank.microvalidation.entities;

import com.tnbank.microvalidation.beans.AccountBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Clock;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Transaction {

    private @Id
    @Size(min = 12, max = 12) String id;
    private @Size(min = 15, max = 15) String idReq;
    private Boolean validated = false;
    private String customerPresentId = "T2XNotNeeded";
    private String beneficiaryId = "";
    private String sourceId = "";
    private String description = "no specific description given";
    private @NotNull @Min(50) Long amount;
    private LocalDateTime timestamp = LocalDateTime.now(Clock.systemUTC());
    private @NotNull @Size(min = 3, max = 3) String typeCode;
    private String frequency = "";
    private LocalDateTime endTimestamp = LocalDateTime.now(Clock.systemUTC());
    private int priority = 100;

    public void validate(AccountBean fromAccount, AccountBean toAccount) {
        priorityAccount(toAccount);
        priorityAccount(fromAccount);
        switch (typeCode) {
            case "DEP":
                priority += 10;
                if (amount > 3000)
                    priority += 10;
                break;
            case "WIT":
                priority -= 10;
                if (amount > 3000)
                    priority += 5;
                break;
            case "T2X":
                priority += 5;
                break;
        }
    }

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
}
