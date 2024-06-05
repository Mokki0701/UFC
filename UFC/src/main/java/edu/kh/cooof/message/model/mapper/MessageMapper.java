package edu.kh.cooof.message.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.cooof.member.model.dto.Member;
import edu.kh.cooof.message.model.dto.Message;

@Mapper
public interface MessageMapper {

	int getListCount(int memberNo);

	List<Message> selectMessageList(int memberNo, RowBounds rowBounds);

	int getRevListCount(int memberNo);

	List<Message> selectRevMessageList(int memberNo, RowBounds rowBounds);

	int deleteMessage(int messageNo);

	Message detailMessage(int messageNo);

	void updateStatus(Map<String, Integer> map);

	int checkRead(int messageNo);

	int getRevMemberNo(String memberEmail);

	int sendMessage(Message message);

	String getRevMemberEmail(String memberName);

	int blockMessage(Map<String, Integer> map);

	int getBlockMemberNo(String memberName);

	int blockCheck(Map<String, Integer> map);

	List<Member> blockMemberList(int memberNo);

	String getMemberEmail(int memberNo);

	int getMemberNo(String memberEmail);

	int unblockMember(Map<String, Integer> map);

}
