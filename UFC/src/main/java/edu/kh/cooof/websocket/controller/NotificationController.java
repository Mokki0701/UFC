package edu.kh.cooof.websocket.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.member.model.dto.Member;
import edu.kh.cooof.websocket.model.dto.Notification;
import edu.kh.cooof.websocket.model.service.NotificationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("notification")
public class NotificationController {

	private final NotificationService service;
	
	@GetMapping("notReadCheck")
	public int notReadCheck(
			@SessionAttribute("loginMember") Member loginMember
			) {
		return service.netReadCheck(loginMember.getMemberNo());
	}
	
	@GetMapping("")
	public List<Notification> selectNotification(
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		List<Notification> list = service.selectNotification(loginMember.getMemberNo());
				
		return service.selectNotification(loginMember.getMemberNo());
	}
	
	@PutMapping("")
	public void updateNotification(
			@RequestBody int notificationNo
			) {
		service.updateNotification(notificationNo);
	}
	
	@DeleteMapping("")
	public int deleteNotification(
			@RequestBody int notificationNo, @SessionAttribute("loginMember") Member loginMember
			) {
		return service.deleteNotification(notificationNo, loginMember.getMemberNo());
	}
	
	
	
	
}
