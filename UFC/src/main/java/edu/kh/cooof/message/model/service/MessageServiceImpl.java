package edu.kh.cooof.message.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.list.model.dto.LessonPagination;
import edu.kh.cooof.member.model.dto.Member;
import edu.kh.cooof.message.model.dto.Message;
import edu.kh.cooof.message.model.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {

	private final MessageMapper mapper;
	
	@Override
	public Map<String, Object> selectMessageList(int memberNo, int cp) {
		
		Map<String, Object> map = new HashMap<>();
		
		int listCount = mapper.getListCount(memberNo);
		
		LessonPagination pagination = new LessonPagination(1, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Message> messageList = mapper.selectMessageList(memberNo, rowBounds);
		
		map.put("pagination", pagination);
		map.put("messageList", messageList);
		
		return map;
	}
	
	@Override
	public Map<String, Object> selectRevMessageList(int memberNo, int cp) {

		Map<String, Object> map = new HashMap<>();
		
		int listCount = mapper.getRevListCount(memberNo);
		
		LessonPagination pagination = new LessonPagination(1, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Message> messageList = mapper.selectRevMessageList(memberNo, rowBounds);
		
		map.put("pagination", pagination);
		map.put("messageList", messageList);
		
		return map;
	}
	
	@Override
	public int deleteMessage(int messageNo) {
	
		return mapper.deleteMessage(messageNo);
	}
	
	@Override
	public Message detailMessage(int messageNo, int memberNo) {
		
		int checkRead = mapper.checkRead(messageNo);
		
		Map	<String, Integer> map = new HashMap<>();
		map.put("messageNo", messageNo);
		map.put("memberNo", memberNo);
				
		if(checkRead > 0) {
			
			mapper.updateStatus(map);
			
		}
		
		
		return mapper.detailMessage(messageNo);
	}
	
	@Override
	public int sendMessage(Message message) {
		
		int memberRev = mapper.getRevMemberNo(message.getMemberEmail());
		
		message.setMessageRev(memberRev);
		
		int reseult = mapper.sendMessage(message);
		
		return mapper.getMessageNo();
	}
	
	@Override
	public String getRevMemberEmail(String memberName) {
		
		return mapper.getRevMemberEmail(memberName);
	}
	
	@Override
	public int blockMessage(int memberNo, int loginMemberNo) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("memberNo", memberNo);
		map.put("loginMemberNo", loginMemberNo);
		
		int result = mapper.blockCheck(map);
		if(result > 0) {
			return 0;
		}
		
		return mapper.blockMessage(map);
	}
	
	
	@Override
	public int getBlockMemberNo(String memberName) {
		
		return mapper.getBlockMemberNo(memberName);
	}
	
	@Override
	public List<Member> blockMemberList(int memberNo) {
		
		List<Member> blockMemberList = mapper.blockMemberList(memberNo);
		
		for(Member member : blockMemberList) {
			
			member.setMemberEmail(mapper.getMemberEmail(member.getBlockBeing()));
			
		}
		
		return blockMemberList;
	}
	
	@Override
	public int unblockMember(String memberEmail, int memberNo) {
		
		int blockedMemberNo = mapper.getMemberNo(memberEmail);
		
		Map<String, Integer> map = new HashMap<>();
		map.put("memberNo", memberNo);
		map.put("blockedMemberNo", blockedMemberNo);
		
		return mapper.unblockMember(map);
	}
	
	
	
	
	
}
