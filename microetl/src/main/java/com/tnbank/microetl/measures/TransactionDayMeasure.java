package com.tnbank.microetl.measures;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Clock;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
public class TransactionDayMeasure {

    private @Id LocalDate day = LocalDate.now(Clock.systemUTC());
    private long todayNet = 0;
    private long todayAmount = 0;
    private long todayCount = 0;
    private long allNet = 0;
    private long allAmount = 0;
    private long allCount = 0;

}
