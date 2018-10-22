package com.tnbank.agentui.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RequestDayMeasureBean {
    private LocalDate day;
    private long todayReqAmount;
    private long todayReqCount;
    private long allReqAmount;
    private long allReqCount;
}
