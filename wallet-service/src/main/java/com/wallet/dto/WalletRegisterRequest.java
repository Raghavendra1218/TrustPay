package com.wallet.dto;

import lombok.Data;

@Data
public class WalletRegisterRequest {
    private String passcode;
    private String confirmPasscode;
    private String otp;
}
