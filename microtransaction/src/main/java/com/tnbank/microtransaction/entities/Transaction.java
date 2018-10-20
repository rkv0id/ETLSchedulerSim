package com.tnbank.microtransaction.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Transaction {
    private @Id
    @Size(min = 12, max = 12) String id;
    private String customerPresentId = "T2XNotNeeded";
    private String beneficiaryId = "NONE";
    private String sourceId = "NONE";
    private String description = "no specific description given";
    private @NotNull @Min(50) Long amount;
    private LocalDateTime timestamp = LocalDateTime.now(Clock.systemUTC());
    private @NotNull @Size(min = 3, max = 3) String typeCode;
    private String frequency = "NONE";
    private LocalDate endTimestamp = LocalDate.now(Clock.systemUTC());
}
