package edu.kh.cooof.gym.application.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.cooof.gym.application.model.dto.Application;

@Mapper
public interface ApplicationMapper {

	int insertApplication(Application inputApply);
	
	
}
