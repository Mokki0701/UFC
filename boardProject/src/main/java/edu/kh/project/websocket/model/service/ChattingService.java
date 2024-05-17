package edu.kh.project.websocket.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.websocket.model.dto.ChattingRoom;
import edu.kh.project.websocket.model.dto.Message;

public interface ChattingService {

    List<ChattingRoom> selectRoomList(int memberNo);

    int checkChattingNo(Map<String, Integer> map);

    int createChattingRoom(Map<String, Integer> map);


    int insertMessage(Message msg);

    int updateReadFlag(Map<String, Object> paramMap);

    List<Message> selectMessageList( Map<String, Object> paramMap);

	List<Member> selectTarget(Map<String, Object> map);

}
