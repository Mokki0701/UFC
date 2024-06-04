package edu.kh.cooof.gym.trainerSelect.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.cooof.gym.trainerSelect.model.dto.PaymentRequest;
import edu.kh.cooof.gym.trainerSelect.model.dto.Trainer;
import edu.kh.cooof.gym.trainerSelect.model.service.TrainerSelectService;
import edu.kh.cooof.member.model.dto.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("trainerSelect")
@Slf4j
public class TrainerSelectController {

	private final TrainerSelectService service;
	
	// 트레이너 선택 페이지
	@GetMapping("trainerSelect")
	public String trainerSelect(
			Model model,
			HttpSession session,
			RedirectAttributes ra) {
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		String path = null;	
		String message = null;
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "트레이너 페이지는 로그인 후 사용 가능합니다.");
			path = "redirect:/gym/gymMain";
			
		} else {
			List<Trainer> trainers = service.getAllTrainers();	
			model.addAttribute("trainers", trainers);
			path = "gym/trainerSelect/trainerSelect";
		}
		
	    return path;
	}
	
	@PostMapping("trainerPrice")
	public String trainerPrice(
			@RequestParam("ptCount") int ptCount,
			@RequestParam("trainerNo") int trainerNo,
			HttpSession session,
			Model model){
		
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		
		
		if(loginMember != null) {
			
			 int memberNo = loginMember.getMemberNo(); 
			 String memberLname = loginMember.getMemberLastName();
			 String memberFname = loginMember.getMemberFirstName();
			 String memberGender = loginMember.getMemberGender();
			 
			 Trainer trainer = service.selectTrainer(trainerNo);
			
			model.addAttribute("trainer", trainer);
			model.addAttribute("calcResult", trainer.getTrainerPrice() * ptCount);
			model.addAttribute("member", loginMember);
			

			return "/gym/trainerSelect/trainerPrice"; // ${param.ptCount}
		}
		
		return "/gym/trainerSelect/trainerSelect";
	}
  
	@GetMapping("apply")
	public String apply(){
		
		return "gym/trainerSelect/apply";
	}
	
	
	
	
	@ResponseBody
	@PostMapping("gymPay")
	public int gymPayment(@RequestBody PaymentRequest request                    
	                          ) {
		
	    return service.gymPayment(request);
	    
	}
		
	
    
	
	
	
}
