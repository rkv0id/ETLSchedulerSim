package com.tnbank.microtransaction.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DimTransactionRequestBean {
    private String id;
    private Long amount;
    private LocalDateTime timestamp;
    private String typeCode;
}