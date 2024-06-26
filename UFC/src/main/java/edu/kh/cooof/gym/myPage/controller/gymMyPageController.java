package edu.kh.cooof.gym.myPage.controller;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.cooof.gym.myPage.model.dto.GymMyPage;
import edu.kh.cooof.gym.myPage.model.service.gymMyPageService;
import edu.kh.cooof.member.model.dto.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gym/myPage")
public class gymMyPageController {

    private final gymMyPageService service;

    // 헬스장 내 정보
    @GetMapping("/gymMyPage")
    public String gymMyPage(
            @SessionAttribute("loginMember") Member loginMember,
            Model model) {

        // 세션에서 memberNo를 가져옴
        int memberNo = loginMember.getMemberNo();

        // 서비스 호출하여 PT 정보 조회
        GymMyPage gymMyPage = service.getPTInfo(memberNo);

        // PT 정보가 없는 경우 기본 메시지를 모델에 추가
        if (gymMyPage == null) {
            model.addAttribute("message", "PT 신청 내역이 없습니다.");
        } else {
            // PT 시작일과 현재 시간을 비교하여 PT 진행 횟수를 계산
            Date ptStartDate = gymMyPage.getPtStrDate();
            LocalDate startDate = ptStartDate.toLocalDate();
            LocalDate now = LocalDate.now();

            int completedPT;
            if (now.isBefore(startDate)) {
                // 현재 날짜가 PT 시작일보다 이전인 경우
                completedPT = 0;
            } else {
                long daysBetween = Duration.between(startDate.atStartOfDay(), now.atStartOfDay()).toDays();
                completedPT = (int) daysBetween;

                // 최대 PT 횟수를 초과하지 않도록 조정
                if (completedPT > gymMyPage.getPtYN()) {
                    completedPT = gymMyPage.getPtYN();
                }
            }
            
         // PT 진행 횟수가 PT 최대 횟수와 같아지면 gymMyPage를 null로 설정
            if (completedPT == gymMyPage.getPtYN()) {
                gymMyPage = null;
                model.addAttribute("message", "PT가 완료되었습니다.");
            } else {
            	
            	// PT 진행 횟수를 설정
                gymMyPage.setPtCount(completedPT);
            }

            model.addAttribute("gymMyPage", gymMyPage);
        }

        return "gym/myPage/gymMyPage";
    }
    
    
    // 지난 PT 기록
    @GetMapping("/ptRecord")
    public String ptRecord(
            @SessionAttribute("loginMember") 
            Member loginMember,
            Model model
            ) {

        // 세션에서 memberNo를 가져옴
        int memberNo = loginMember.getMemberNo();

        // 서비스 호출하여 모든 PT 기록 조회
        List<GymMyPage> ptRecords = service.getAllPTRecords(memberNo);

        // PT 기록을 모델에 추가
        model.addAttribute("ptRecords", ptRecords);

        return "gym/myPage/ptRecord";
    }

    
}
