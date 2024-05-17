package edu.kh.project.websocket.model.service;

import java.util.List;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.websocket.model.dto.Notification;

public interface NotificationService {

	/** 받은 알림 조회
	 * @param receiveMemberNo
	 * @return
	 */
	List<Notification> selectNotification(int receiveMemberNo);

	/** 알림을 보낼 때 필요한 게시글 관련 값  조회
	 * @param pkNo
	 * @return
	 */
	Board selectBoardData(int pkNo);

	
	/** 알림 삽입
	 * @param notification
	 * @return result
	 */
	int insertNotification(Notification notification);

	/**알림 읽음으로 변경
	 * @param notificationNo
	 */
	void updateNotification(int notificationNo);

	/** 읽지 않은 알림 개수 조회
	 * @param memberNo
	 * @return
	 */
	int notReadCheck(int memberNo);

	/** 알림 삭제
	 * @param notificationNo
	 * @param memberNo
	 * @return 읽지 않은 알림 개수
	 */
	int deleteNotification(int notificationNo, int memberNo);
}
