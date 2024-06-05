package edu.kh.cooof.message.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	
	
}
