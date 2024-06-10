package edu.kh.cooof.websocket.model.service;

import edu.kh.cooof.message.model.dto.Message;
import edu.kh.cooof.websocket.model.dto.Notification;

public interface NotificationService {

	int insertNotification(Notification notification);

	Message selectMessage(String messageNo);

}
