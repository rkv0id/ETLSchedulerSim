package com.tnbank.microetl.entities;

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
@Data
@NoArgsConstructor
public class DimTransaction {

    private @Id
    @Size(min = 12, max = 12) String id;
    private @NotNull @Min(50) Long amount;
    private LocalDateTime timestamp = LocalDateTime.now(Clock.systemUTC());
    private @NotNull @Size(min = 3, max = 3) String typeCode;
}