package edu.kh.project.websocket.handler;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.websocket.model.dto.Message;
import edu.kh.project.websocket.model.service.ChattingService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChattingWebsocketHandler extends TextWebSocketHandler{
	
	// ChattingService Bean 의존성 주입
	private final ChattingService service;
	

	// WebSocketSession : 클라이언트 - 서버 전이중 통신 담당 객체
	//					  (+ 가로챈 클라이언트 세션 담겨있음)
	
	// sessions : 연결된 클라이언트 (== /chatting 페이지에 접속한 사람)
	// 			  들의 접속 정보를 모두 모아두는 용도
	private Set<WebSocketSession> sessions
		= Collections.synchronizedSet(new HashSet<>());
	
	
	// 클라이언트와 연결이 완료되고, 통신할 준비가 되면 실행
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		// 연결된 클라이언트를 sessions에 추가
		sessions.add(session);
	}
	
	
	// 클라이언트와 연결이 종료되면 실행
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
	
		// 연결 종료된 클라이언트를 sessions에서 제거
		sessions.remove(session);
	}
	
	
	// 클라이언트로부터 텍스트 메시지를 전달 받은 경우
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	
		// message 매개변수 : 전달 받은 메시지가 담겨 있음
		
		
		// JSON <-> DTO 변환할 수 있는 Jackson 라이브러리 제공 객체
		ObjectMapper objectMapper = new ObjectMapper();
											 
		Message msg 
			= objectMapper.readValue(message.getPayload(), Message.class);
										// JSON       ->      DTO 타입
		
		// 채팅을 보낸 사람의 회원 번호 얻어오기
        HttpSession currentSession =  (HttpSession)session.getAttributes().get("session");
    	Member sendMember = ((Member)currentSession.getAttribute("loginMember"));
    	msg.setSenderNo(sendMember.getMemberNo());
    	
    	// MSG 세팅 값 : chattingNo, messageContent, sendMember, targetNo
		
    	// DB 삽입 서비스 호출
        int result = service.insertMessage(msg);
        
        if(result == 0) return;
        
        // 채팅이 보내진 시간을 msg 세팅
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm");
        msg.setSendTime(sdf.format(new Date()) );
        
		
		// 연결된 모든 클라이언트를 순차 접근
		for(WebSocketSession s : sessions) {
			
			// 전달받은 Message의 targetNo와
			// 연결된 클라이언트의 회원 번호가 같을 경우
			// == 메시지를 받을 회원인 경우
			
			HttpSession clientSession
				= (HttpSession)s.getAttributes().get("session");
			
			int clientMemberNo 
				=  ((Member)clientSession.getAttribute("loginMember")).getMemberNo();
			
			// msg 객체를 JSON으로 변경한 값을
			// 보낸 사람/받는 사람에게 전달
			if(msg.getTargetNo() == clientMemberNo || msg.getSenderNo() ==  clientMemberNo) {
				TextMessage textMessage 
					= new TextMessage( objectMapper.writeValueAsString(msg) ); 
											//  JSON       <-          DTO
				
				 // JSON으로 변환된 데이터를 지정된 클라이언트에게 전달
				s.sendMessage(textMessage);
			}
		}
	
	}
	
	
	
	
	
	
	
	
	
	
}
