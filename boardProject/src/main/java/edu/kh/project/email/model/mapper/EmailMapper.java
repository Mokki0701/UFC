package edu.kh.project.email.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

	int updateAuthKey(Map<String, String> map);
	/*
	 * public int updateAuthKey(Map<String, String> map){
	 *   return sqlSessionTemplte
	 *   	.update("edu.kh.project.email.model.mapper.EmailMapper", map);
	 * }
	 * 
	 * 
	 * */

	int insertAuthKey(Map<String, String> map);

	int checkAuthKey(Map<String, Object> map);

}
