package com.tnbank.microaccount.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Account {

    private @Id @Size(min = 16, max = 16) String id;
    private @NotNull @Size(min = 8, max = 8) String cinCustomer;
    private Long balance = 0L;
    private Long creditLine = 250000L;
    private Long beginBalance = 0L;
    private LocalDateTime beginTimestamp = LocalDateTime.now();
    private @NotNull @Size(min = 4, max = 4) String typeCode = "NORM";
    private @NotNull @Size(min = 3, max = 3) String statusCode = "OPN";

}
