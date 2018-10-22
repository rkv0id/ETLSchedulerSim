package com.tnbank.agentui.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AccountTypeMeasureBean {
    private LocalDate day;
    private long todayCount;
    private long allCount;
    private long todayNormCount;
    private long allNormCount;
    private long todayNentCount;
    private long allNentCount;
    private long todayPremCount;
    private long allPremCount;
    private long todayPentCount;
    private long allPentCount;
}
