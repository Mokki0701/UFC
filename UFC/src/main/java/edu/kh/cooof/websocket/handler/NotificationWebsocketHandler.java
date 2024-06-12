package edu.kh.cooof.websocket.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kh.cooof.member.model.dto.Member;
import edu.kh.cooof.message.model.dto.Message;
import edu.kh.cooof.websocket.model.dto.Notification;
import edu.kh.cooof.websocket.model.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationWebsocketHandler extends TextWebSocketHandler {

	private final NotificationService service;
	
	private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<WebSocketSession>());
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Notification notification = objectMapper.readValue(message.getPayload(), Notification.class);
		
		HttpSession currentSession = (HttpSession) session.getAttributes().get("session");
		Member sendMember = ((Member)currentSession.getAttribute("loginMember"));
		
		setNotification(notification, sendMember);
		
		if(notification.getNotificationContent() == null) return;
		
		// DB에 알림 삽입
		// 이거 수업 코드 리뷰 해보고 말 그대로 삽입하는 건지 체크하기
		int result = service.insertNotification(notification);
		
		if(result == 0) return;
			
		for(WebSocketSession s : sessions) {
		
			HttpSession temp = (HttpSession) s.getAttributes().get("session");
			int loginMemberNo = ((Member)temp.getAttribute("loginMember")).getMemberNo();
			
			if(loginMemberNo == notification.getReceiveMemberNo()) {
				s.sendMessage(new TextMessage(objectMapper.writeValueAsString(notification)));
			}
		}
	}
	
	
	/** 알림 종류에 따라 알림 객체에 값 추가하기
     * @param notification
     * @return
     */
	private void setNotification(Notification notification, Member sendMember) {
		
		notification.setSendMemberNo(sendMember.getMemberNo());
		
		Message message = service.selectMessage(notification.getMessageNo());
		
		String content = null;
		
		switch(notification.getNotificationType()) {
			
		case "sendMessage" :
			
			content = String.format("<b>%s</b>님이 쪽지를 보냈습니다. <b>%s</b>", 
						   	        sendMember.getMemberEmail(),message.getMessageTitle());
			
			notification.setNotificationContent(content);
			
			notification.setReceiveMemberNo(message.getMessageRev());
			break;
		
		
//		case "completeReservation" :
//			
//			content = String.format("<b>%s</b>님의 열람실 예약 시간 종료 10분전 입니다.", sendMember.getMemberEmail());
//			
//			notification.setNotificationContent(content);
//			
//			notification.setReceiveMemberNo(message.getMessageRev());
//			
//			break;
		
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
