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
public class RequestDayMeasure {

    private @Id LocalDate day = LocalDate.now(Clock.systemUTC());
    private long todayReqAmount = 0;
    private long todayReqCount = 0;
    private long allReqAmount = 0;
    private long allReqCount = 0;

}
