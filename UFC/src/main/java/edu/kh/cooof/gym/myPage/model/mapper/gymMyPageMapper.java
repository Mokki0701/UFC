package edu.kh.cooof.gym.myPage.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.gym.myPage.model.dto.GymMyPage;

@Mapper
public interface gymMyPageMapper {

	GymMyPage selectPTInfo(int memberNo);

	List<GymMyPage> selectAllPTRecords(int memberNo);

}
