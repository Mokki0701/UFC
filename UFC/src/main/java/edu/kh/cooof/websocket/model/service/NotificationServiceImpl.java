package edu.kh.cooof.websocket.model.service;

import org.springframework.stereotype.Service;

import edu.kh.cooof.message.model.dto.Message;
import edu.kh.cooof.websocket.model.dto.Notification;
import edu.kh.cooof.websocket.model.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationMapper mapper;
	
	@Override
	public int insertNotification(Notification notification) {
		
		return mapper.insertNotification(notification);
	}
	
	@Override
	public Message selectMessage(String messageNo) {
		
		return mapper.selectMessage(messageNo);
	}
	
}
