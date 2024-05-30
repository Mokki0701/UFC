package edu.kh.cooof.lib.main.model.service;

import java.util.Map;

public interface LibMemberService {

	Map<String, Object> getMemberInfo(int memberNo);

	int extendBook(int memberNo, int bookNo);

	int insertHopeBook(Map<String, Object> paramMap);

}
