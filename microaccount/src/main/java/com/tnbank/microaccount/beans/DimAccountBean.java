package com.tnbank.microaccount.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class DimAccountBean {

    private String id;
    private String cinCustomer;
    private Long balance;
    private LocalDateTime beginTimestamp;
    private String typeCode;

}