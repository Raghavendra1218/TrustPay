package com.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageSender {
	@Autowired
private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private ObjectMapper objectMapper;

	public void sendOtp(String email,String otp) {
		kafkaTemplate.send("CREATE_WALLET",email,otp);
		
		System.out.println("send to kafka");
	}
}