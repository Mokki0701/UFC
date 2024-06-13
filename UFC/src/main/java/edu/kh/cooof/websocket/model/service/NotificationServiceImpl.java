package edu.kh.cooof.websocket.model.service;

import java.util.List;

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
	public Message selectMessage(int messageNo) {
		
		return mapper.selectMessage(messageNo);
	}
	
	@Override
	public int deleteNotification(int notificationNo, int memberNo) {
		
		int result = mapper.deleteNotification(notificationNo);
		
		if(result > 0) return mapper.notReadCheck(memberNo);
		
		return 0;
	}
	@Override
	public int netReadCheck(int memberNo) {
		return mapper.notReadCheck(memberNo);
	}
	@Override
	public List<Notification> selectNotification(int memberNo) {
		return mapper.selectNotification(memberNo);
	}
	@Override
	public int updateNotification(int notificationNo) {
		return mapper.updateNotification(notificationNo);
	}
}
