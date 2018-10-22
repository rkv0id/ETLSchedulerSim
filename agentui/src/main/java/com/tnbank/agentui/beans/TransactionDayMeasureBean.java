package com.tnbank.agentui.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransactionDayMeasureBean {
    private LocalDate day;
    private long todayNet;
    private long todayAmount;
    private long todayCount;
    private long allNet;
    private long allAmount;
    private long allCount;
}
