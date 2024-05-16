package edu.kh.project.websocket.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.websocket.model.dto.ChattingRoom;
import edu.kh.project.websocket.model.dto.Message;

@Mapper
public interface ChattingMapper {
    public List<ChattingRoom> selectRoomList(int memberNo);

    public int checkChattingNo(Map<String, Integer> map);

    public int createChattingRoom(Map<String, Integer> map);


    public int insertMessage(Message msg);

    public int updateReadFlag(Map<String, Object> paramMap);

    public List<Message> selectMessageList(int chattingNo);

	public List<Member> selectTarget(Map<String, Object> map);
}
