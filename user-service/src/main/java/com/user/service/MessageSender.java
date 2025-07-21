package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.dto.UserDto;
import com.user.constants.AppConstants;
@Component
public class MessageSender {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
		@Autowired
		private ObjectMapper objectMapper;
		public void sendNotification(UserDto userDto) {
			try {
				String jsonText=objectMapper.writeValueAsString(userDto);
				System.out.println(jsonText);
				kafkaTemplate.send(AppConstants.NEW_USER,userDto.getUserName(),jsonText);
				System.out.println("send to kafka");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
}
