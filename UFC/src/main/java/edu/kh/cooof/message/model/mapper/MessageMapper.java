package edu.kh.cooof.message.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.cooof.message.model.dto.Message;

@Mapper
public interface MessageMapper {

	int getListCount(int memberNo);

	List<Message> selectMessageList(int memberNo, RowBounds rowBounds);

	int getRevListCount(int memberNo);

	List<Message> selectRevMessageList(int memberNo, RowBounds rowBounds);

	int deleteMessage(int messageNo);

}
