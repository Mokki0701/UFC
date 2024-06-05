package edu.kh.cooof.message.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.cooof.message.model.dto.Message;

public interface MessageService {

	Map<String, Object> selectMessageList(int memberNo, int cp);

	Map<String, Object> selectRevMessageList(int memberNo, int cp);

	int deleteMessage(int messageNo);

	Message detailMessage(int messageNo, int memberNO);

	int sendMessage(Message message);

	

}
