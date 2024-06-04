package edu.kh.cooof.websocket.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kh.cooof.member.model.dto.Member;
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
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Notification notification = objectMapper.readValue(message.getPayload(), Notification.class);
		
		HttpSession currentSession = (HttpSession) session.getAttributes().get("session");
		Member sendMember = ((Member)currentSession.getAttribute("loginMember"));
		
		setNotification(notification, sendMember);
		
		if(notification.getNotificationContent() == null) return;
		
		// DB에 알림 삽입
		
		
		
		
		
		
	}
	
	
	/** 알림 종류에 따라 알림 객체에 값 추가하기
     * @param notification
     * @return
     */
	private void setNotification(Notification notification, Member sendMember) {
		
		notification.setSendMemberNo(sendMember.getMemberNo());
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
