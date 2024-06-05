package edu.kh.cooof.message.controller;

import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.member.model.dto.Member;
import edu.kh.cooof.message.model.dto.Message;
import edu.kh.cooof.message.model.service.MessageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("message")
public class MessageController {

	private final MessageService service;
	
	@GetMapping("select")
	public String selectMessage(
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp,
			@RequestParam(value="type", required = false, defaultValue = "0") int type,
			Model model
			) {
		
		// 이거 타입에 따라서 쪽지 리스트에 들어가야 이메일에 들어가야 할 값을 보낸 사람이메일인지 받은 사람 이메일인지 확인 해야함
		
		if(type == 0) {
			
			Map<String, Object> map = service.selectMessageList(loginMember.getMemberNo(), cp);
			
			model.addAttribute("pagination", map.get("pagination"));
			model.addAttribute("messageList", map.get("messageList"));
			
		}
		
		else {
			
			Map<String, Object> map = service.selectRevMessageList(loginMember.getMemberNo(), cp);
			
			model.addAttribute("pagination", map.get("pagination"));
			model.addAttribute("messageList", map.get("messageList"));
			
		} 
		
		
		return "common/main/message :: messageSelect";
	}
	
	@GetMapping("delete")
	@ResponseBody
	public int deleteMessage(
			@RequestParam("messageNo") int messageNo
			) {
		
		return service.deleteMessage(messageNo);
	}
	
	@GetMapping("detail")
	public String detailMessage(
			@RequestParam("messageNo") int messageNo,
			@SessionAttribute("loginMember") Member loginMember,
			Model model
			) {
		
		Message message = service.detailMessage(messageNo, loginMember.getMemberNo());
		
		model.addAttribute("realMessage", message);
		
		return "common/main/messageDetail";
	}
	
	@GetMapping("send")
	public String sendMessage(
			@RequestParam(value="memberEmail", required = false) String memberEmail,
			@RequestParam(value="memberName", required = false) String memberName,
			Model model
			) {
		
		if(memberName != null) {
			memberEmail = service.getRevMemberEmail(memberName);
		}
		
		model.addAttribute("memberEmail", memberEmail);
		
		return "common/main/messageSend";
	}
	
	@PostMapping("sendMessage")
	@ResponseBody
	public int sendMessage(
			@RequestBody Message message,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		message.setMessageSen(loginMember.getMemberNo());
		
		
		return service.sendMessage(message);
	}
	
	@GetMapping("block")
	@ResponseBody
	public int blockMessage(
			@RequestParam(value="memberName", required = false) String memberName,
			@SessionAttribute("loginMember") Member loginMember,
			Model model
			) {
		
		int memberNo = service.getBlockMemberNo(memberName);
		
		return service.blockMessage(memberNo, loginMember.getMemberNo());
	}
	
	@GetMapping("blockMember")
	@Async
	public String blocMember(
			@SessionAttribute("loginMember") Member loginMember,
			Model model
			) {
		
		List<Member> emailList = service.blockMemberList(loginMember.getMemberNo());
		
		model.addAttribute("emailList", emailList);
		
		return "common/main/message :: blockMemberList";
	}
	
	@GetMapping("unblockMember")
	@ResponseBody
	public int unblockMember(
			@RequestParam("memberEmail") String memberEmail,
			@SessionAttribute("loginMember") Member loginMember
			) {
		
		return service.unblockMember(memberEmail, loginMember.getMemberNo());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
