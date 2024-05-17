package edu.kh.project.websocket.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.member.model.dto.Member;
import edu.kh.project.websocket.model.dto.Notification;
import edu.kh.project.websocket.model.service.NotificationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationWebsocketHandler extends TextWebSocketHandler{

	private final NotificationService service;
	
    // WebSocketSession : 클라이언트 - 서버간 전이중통신을 담당하는 객체 (JDBC Connection과 유사)
    private Set<WebSocketSession> sessions  = Collections.synchronizedSet(new HashSet<WebSocketSession>());
    
    // afterConnectionEstablished - 클라이언트와 연결이 완료되고, 통신할 준비가 되면 실행. 
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
    
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
   
    	// Jackson 라이브러리 : Java에서 JSON을 다룰수 있는 클래스/메서드를 제공하는 라이브러리 (Spring Boot는 기본 내장)
       
    	// ObjectMapper :
    	// - Jackson에서 제공하는 객체
    	// - JSON <-> DTO Object 변경 가능
    	
        ObjectMapper objectMapper = new ObjectMapper();
        
        // TextMessage로 전달 받은 JSON 데이터를 Notification 객체로 변경
        Notification notification = objectMapper.readValue(message.getPayload(), Notification.class);
    	
        
        
        // 웹소켓 요청을 보낸 회원 정보 얻어오기
    	HttpSession currentSession =  (HttpSession)session.getAttributes().get("session");
    	Member sendMember = ((Member)currentSession.getAttribute("loginMember"));
    	
    	// 알림 객체(notification)에 필요한 값 세팅
    	setNotification(notification, sendMember);
    	
    	
    	
    	log.info("전달 받은 내용 : {}",notification);
    	
    	
    	// 알림 내용이 없는 경우 == 자신의 게시물
    	if(notification.getNotificationContent() == null) return;
    	
    	
    	
    	// DB에 알림 삽입
    	int result = service.insertNotification(notification);
    	
    	if(result == 0) return;
    
    	// /notification/send 로 연결된 객체를 만든 클라이언트들(sessions) 중
		// 회원 번호가 받는 회원 번호와 같은 사람에게 메시지 전잘
		for(WebSocketSession s : sessions) {
			
			HttpSession temp = (HttpSession)s.getAttributes().get("session");
            int loginMemberNo = ((Member)temp.getAttribute("loginMember")).getMemberNo();
			
            if(loginMemberNo == notification.getReceiveMemberNo()) {
            	s.sendMessage(new TextMessage(objectMapper.writeValueAsString(notification)));
            }
		}
    }
    
    
    
    // afterConnectionClosed - 클라이언트와 연결이 종료되면 실행된다.
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        //log.info("{}연결끊김",session.getId());
    }
    
    
    
    
    /** 알림 종류에 따라 알림 객체에 값 추가하기
     * @param notification
     * @return
     */
    private void setNotification(Notification notification, Member sendMember) {
    	
    	// 보낸 사람 받는 번호
    	notification.setSendMemberNo(sendMember.getMemberNo()); 
    	
    	// 보낸 사람 프로필 이미지
    	notification.setSendMemberProfileImg(sendMember.getProfileImg());
    	
    	
    	// 알림을 보낼 때 필요한 게시글 관련 값 조회
    	Board board = service.selectBoardData(notification.getPkNo());

    	
    	// *****************************************************************
    	// 로그인한 회원이 자신의 게시글을 좋아요, 댓글 작성 한 경우 -> 알림 필요 없음
		if(sendMember.getMemberNo() == board.getMemberNo()) return;
		// *****************************************************************
    	

		
    	String content = null;
    	
    	
      	switch(notification.getNotificationType()) {
      	
      	/* ********* 게시글 좋아요 웹소켓 send 요청 시 ********* */
    	case "boardLike" : 
    		
    		// 알림 내용 가공
    		content = String.format("<b>%s</b>님이 <b>[%s]</b> 게시글을 좋아합니다", 
    				sendMember.getMemberNickname(), board.getBoardTitle());
    		
    		// 알림 내용 세팅
    		notification.setNotificationContent(content);
    		
    		// 알림 받을 회원 번호 세팅
    		notification.setReceiveMemberNo(board.getMemberNo());
    		break;
    		
    		
		/* ********* 댓글 등록 웹소켓 send 요청 시 ********* */	
    	case "insertComment" :
    		
			// 알림 내용 가공
			content = String.format("<b>%s</b>님이 <b>[%s]</b> 게시글에 댓글을 남겼습니다", 
    				sendMember.getMemberNickname(), board.getBoardTitle());
			// 알림 내용 세팅
    		notification.setNotificationContent(content);
    		
    		// 알림 받을 회원 번호 세팅
    		notification.setReceiveMemberNo(board.getMemberNo());
    			
    		break;
    	}
    }
}