package edu.kh.cooof.gym.application.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.cooof.gym.application.model.dto.Application;

@Mapper
public interface ApplicationMapper {
	
	
    // 지원서 삽입
    int insertApplication(Application application);

    // 지원서 조회
    Application findApplicationById(@Param("applicationNo") int applicationNo);
	
}
