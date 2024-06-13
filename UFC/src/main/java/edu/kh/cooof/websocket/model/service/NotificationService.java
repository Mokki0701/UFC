package edu.kh.cooof.websocket.model.service;

import java.util.List;

import edu.kh.cooof.message.model.dto.Message;
import edu.kh.cooof.websocket.model.dto.Notification;

public interface NotificationService {

	int insertNotification(Notification notification);

	Message selectMessage(int messageNo);

	int netReadCheck(int memberNo);

	List<Notification> selectNotification(int memberNo);

	int updateNotification(int notificationNo);

	int deleteNotification(int notificationNo, int memberNo);

}
