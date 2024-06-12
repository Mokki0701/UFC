package edu.kh.cooof.websocket.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.message.model.dto.Message;
import edu.kh.cooof.websocket.model.dto.Notification;

@Mapper
public interface NotificationMapper {

	int insertNotification(Notification notification);

	Message selectMessage(int messageNo);
	
	

	int deleteNotification(int notificationNo);

	int notReadCheck(int memberNo);

	List<Notification> selectNotification(int memberNo);

	void updateNotification(int notificationNo);

}
