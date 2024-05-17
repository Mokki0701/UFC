package edu.kh.project.websocket.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.websocket.model.dto.Notification;

@Mapper
public interface NotificationMapper {

	List<Notification> selectNotification(int receiveMemberNo);

	/** 알림을 보낼 때 필요한 게시글 관련 값 조회
	 * @param pkNo
	 * @return
	 */
	Board selectBoardData(int pkNo);
	
	int insertNotification(Notification notification);

	void updateNotification(int notificationNo);

	// 읽지 않은 알림 개수 조회
	int notReadCheck(int memberNo);

	/** 알림 삭제
	 * @param notificationNo
	 * @return 읽지 않은 알림 개수
	 */
	int deleteNotification(int notificationNo);


}
