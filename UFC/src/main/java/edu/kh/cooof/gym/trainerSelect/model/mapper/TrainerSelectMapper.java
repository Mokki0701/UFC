package edu.kh.cooof.gym.trainerSelect.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.cooof.gym.trainerSelect.model.dto.PaymentRequest;
import edu.kh.cooof.gym.trainerSelect.model.dto.PtPrice;
import edu.kh.cooof.gym.trainerSelect.model.dto.Trainer;

@Mapper
public interface TrainerSelectMapper {

	/** 트레이너 정보 불러오기
	 * @return
	 */
	List<Trainer> getAllTrainers();

	/** 트레이너 조회
	 * @param trainerNo
	 * @return
	 */
	Trainer selectTrainer(int trainerNo);

	/** db로 값 넣기
	 * @param request
	 * @return
	 */
	int gymPayment(PaymentRequest request);
	
	// 로그인한 회원 pt 정보 조회
		PtPrice selectPtPriceByMemberNo(int memberNo);

	
	

	

}
