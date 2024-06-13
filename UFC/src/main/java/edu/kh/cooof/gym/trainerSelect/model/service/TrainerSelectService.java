package edu.kh.cooof.gym.trainerSelect.model.service;

import java.util.List;

import edu.kh.cooof.gym.trainerSelect.model.dto.PaymentRequest;
import edu.kh.cooof.gym.trainerSelect.model.dto.PtPrice;
import edu.kh.cooof.gym.trainerSelect.model.dto.Trainer;
import edu.kh.cooof.member.model.dto.Member;

public interface TrainerSelectService {

	/** 트레이너 값 불러오기
	 * @return
	 */
	List<Trainer> getAllTrainers();

	/** 트레이너 조회
	 * @param trainerNo
	 * @return
	 */
	Trainer selectTrainer(int trainerNo);

	

	/** 결제 값 가져오기
	 * @param request
	 * @return 
	 */
	int gymPayment(PaymentRequest request);

	
	// 로그인한 회원의 pt 정보
	PtPrice getPriceByMemberNo(int memberNo);
	
	

	

	
	

	

	
}
