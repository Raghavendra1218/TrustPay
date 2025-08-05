package com.transaction.dto;

import lombok.Data;

@Data

public class SendMoneyRequest {
  
    private Integer receiverId;
    private Float amount;
}

