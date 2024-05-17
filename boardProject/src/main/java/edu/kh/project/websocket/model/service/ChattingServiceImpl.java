package edu.kh.project.websocket.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.common.util.Utility;
import edu.kh.project.member.model.dto.Member;
import edu.kh.project.websocket.model.dto.ChattingRoom;
import edu.kh.project.websocket.model.dto.Message;
import edu.kh.project.websocket.model.dto.Message;
import edu.kh.project.websocket.model.mapper.ChattingMapper;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ChattingServiceImpl implements ChattingService{

    private final ChattingMapper mapper;

    @Override
    public List<ChattingRoom> selectRoomList(int memberNo) {
        return mapper.selectRoomList(memberNo);
    }
    
    @Override
    public int checkChattingNo(Map<String, Integer> map) {
        return mapper.checkChattingNo(map);
    }

    @Override
    public int createChattingRoom(Map<String, Integer> map) {
    	int result = mapper.createChattingRoom(map);
    	
        return result <= 0 ? 0 : (int)map.get("chattingNo"); 
    }


    @Override
    public int insertMessage(Message msg) {
        msg.setMessageContent(Utility.XSSHandling(msg.getMessageContent()));
        return mapper.insertMessage(msg);
    }

    @Override
    public int updateReadFlag(Map<String, Object> paramMap) {
        return mapper.updateReadFlag(paramMap);
    }

    @Override
    public List<Message> selectMessageList( Map<String, Object> paramMap) {
        System.out.println(paramMap);
        List<Message> messageList = mapper.selectMessageList(  Integer.parseInt( String.valueOf(paramMap.get("chattingNo") )));
        
        if(!messageList.isEmpty()) {
            int result = mapper.updateReadFlag(paramMap);
        }
        return messageList;
    }

	@Override
	public List<Member> selectTarget(Map<String, Object> map) {
		return mapper.selectTarget(map);
	}


    
    
}
