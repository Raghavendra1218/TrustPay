package com.transaction.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data

@AllArgsConstructor
@NoArgsConstructor
public class PassbookEntry {
    private Integer id;
    private String type; 
    private Integer partyId; 
    private Float amount;
    private String status;
    private String remarks;
    private LocalDateTime timestamp;

    
}
