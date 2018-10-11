package com.tnbank.microvalidation.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AccountBean {
    private String id;
    private String cinCustomer;
    private Long balance;
    private Long creditLine;
    private Long beginBalance;
    private LocalDateTime beginTimestamp;
    private String typeCode;
    private String statusCode;
}
