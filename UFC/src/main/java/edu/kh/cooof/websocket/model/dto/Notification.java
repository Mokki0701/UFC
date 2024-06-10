package edu.kh.cooof.websocket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Notification {

	private int notificationNo;
	private String notificationContent;
	private String notificationCheck;
	private String notificationDate;
	private String notificationUrl;
	private String sendMemberProfileImg;
	private int sendMemberNo;		// 알림 보낸 사람
	private int receiveMemberNo;    // 알림 받는 사람 (중요!)
	
	private String notificationType; // 알림 내용을 구분해서 만드는 용도
	
	private String messageNo;		 // 알림이 보내진 쪽지 번호
	private String messageTitle; 	 // 알림이 보내진 쪽지 제목
	
	
	
}
