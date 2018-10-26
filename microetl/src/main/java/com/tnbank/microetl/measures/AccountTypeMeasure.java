package com.tnbank.microetl.measures;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Clock;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
public class AccountTypeMeasure {

    private @Id LocalDate day = LocalDate.now(Clock.systemUTC());
    private long todayCount = 0;
    private long allCount = 0;
    private long todayNormCount = 0;
    private long allNormCount = 0;
    private long todayNentCount = 0;
    private long allNentCount = 0;
    private long todayPremCount = 0;
    private long allPremCount = 0;
    private long todayPentCount = 0;
    private long allPentCount = 0;

}
