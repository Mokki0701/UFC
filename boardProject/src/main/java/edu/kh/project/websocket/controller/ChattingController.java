package edu.kh.project.websocket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.websocket.model.dto.ChattingRoom;
import edu.kh.project.websocket.model.dto.Message;
import edu.kh.project.websocket.model.service.ChattingService;
import lombok.RequiredArgsConstructor;

@Controller 
@RequestMapping("chatting")
@RequiredArgsConstructor
public class ChattingController {

	private final ChattingService service;
	
	// 채팅 페이지 이동
	@GetMapping("")
	public String chattingPage(
		@SessionAttribute("loginMember") Member loginMember,
		Model model) {
		
		List<ChattingRoom> roomList = service.selectRoomList(loginMember.getMemberNo());
        model.addAttribute("roomList", roomList);
		
		return "chatting/chatting";
	}
	
	
	
    // 채팅 상대 검색
    @GetMapping(value="selectTarget", produces="application/json; charset=UTF-8")
    @ResponseBody
    public List<Member> selectTarget(
    		@RequestParam("query") String query, 
    		@SessionAttribute("loginMember") Member loginMember){
    	Map<String, Object> map = new HashMap<>();
    	map.put("memberNo", loginMember.getMemberNo());
    	map.put("query", query);
    	return service.selectTarget(map);
    }
    
    // 채팅방 입장(없으면 생성)
    @GetMapping("enter")
    @ResponseBody
    public int chattingEnter(
    	@RequestParam("targetNo") int targetNo, 
    	@SessionAttribute("loginMember") Member loginMember) {
     
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        map.put("targetNo", targetNo);
        map.put("loginMemberNo", loginMember.getMemberNo());
        
        int chattingNo = service.checkChattingNo(map);
        
        if(chattingNo == 0) {
            chattingNo = service.createChattingRoom(map);
        }
        
        return chattingNo;
    }
    
    // 채팅방 목록 조회
    @GetMapping(value="roomList", produces="application/json; charset=UTF-8")
    @ResponseBody
    public List<ChattingRoom> selectRoomList(@SessionAttribute("loginMember") Member loginMember) {
    	return service.selectRoomList(loginMember.getMemberNo());
    }
    
    
    // 채팅 읽음 표시
    @PutMapping("updateReadFlag")
    @ResponseBody
    public int updateReadFlag(@RequestBody Map<String, Object> paramMap, @SessionAttribute("loginMember") Member loginMember) {
    	paramMap.put("memberNo", loginMember.getMemberNo());
        return service.updateReadFlag(paramMap);
    }
    
    // 채팅 메시지 목록 조회
    @GetMapping(value="selectMessage", produces="application/json; charset=UTF-8")
    @ResponseBody
    public List<Message> selectMessageList(@RequestParam Map<String, Object> paramMap, @SessionAttribute("loginMember") Member loginMember) {
    	paramMap.put("memberNo", loginMember.getMemberNo());
        return service.selectMessageList(paramMap);
    }
	
	
	
}

