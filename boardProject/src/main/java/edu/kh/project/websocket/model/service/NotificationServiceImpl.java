package edu.kh.project.websocket.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.websocket.model.dto.Notification;
import edu.kh.project.websocket.model.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

	private final NotificationMapper mapper;
	
	@Override
	public List<Notification> selectNotification(int receiveMemberNo) {
		return mapper.selectNotification(receiveMemberNo);
	}
	
	// 알림을 보낼 때 필요한 게시글 관련 값 조회
	@Override
	public Board selectBoardData(int pkNo) {
		return mapper.selectBoardData(pkNo);
	}
	
	// 알림 삽입
	@Override
	public int insertNotification(Notification notification) {
		return mapper.insertNotification(notification);
	}
	
	@Override
	public void updateNotification(int notificationNo) {
		mapper.updateNotification(notificationNo);
	}
	
	
	// 읽지 않은 알림 개수 조회
	@Override
	public int notReadCheck(int memberNo) {
		return mapper.notReadCheck(memberNo);
	}

	// 알림 삭제
	@Override
	public int deleteNotification(int notificationNo, int memberNo) {
		int result = mapper.deleteNotification(notificationNo);
		
		if(result > 0) return mapper.notReadCheck(memberNo);
		return 0;
	}
}
