package edu.kh.cooof.websocket.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.message.model.dto.Message;
import edu.kh.cooof.websocket.model.dto.Notification;

@Mapper
public interface NotificationMapper {

	int insertNotification(Notification notification);

	Message selectMessage(String messageNo);

}
