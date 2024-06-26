package edu.kh.cooof.gym.management.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.cooof.gym.application.model.dto.Application;

@Mapper
public interface ManagementMapper {

	List<Application> selectApplications();

	Application selectApplicationNo(int applicationNo);

	int updateAuthority(Integer memberNo);

	int updateApplicationStatus(@Param("memberNo") int memberNo, @Param("status") String status);
	
	int insertTrainer(Application app);

}
