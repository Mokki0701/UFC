package edu.kh.cooof.message.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lesson.list.model.dto.LessonPagination;
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
	
	
	
	
	
	
}
