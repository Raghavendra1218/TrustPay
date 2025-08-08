package com.transaction.api;

import com.transaction.dto.SendMoneyRequest;
import com.transaction.service.TransactionService;
import com.transaction.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionApi {

    @Autowired
    private TransactionService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/transfer")
    public ResponseEntity<?> sendMoney(@RequestBody SendMoneyRequest request,
                                       @RequestHeader("Authorization") String token,@RequestParam String passcode) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing Authorization header");
            }

            String jwt = token.substring(7);
            Integer senderId = jwtUtil.extractUserId(jwt);

            if (request.getAmount() == null || request.getAmount() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Amount must be greater than 0");
            }
           
            return service.fundTransfer(senderId, request.getReceiverId(), request.getAmount(),passcode);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Transfer failed: " + e.getMessage());
        }
    }
}
