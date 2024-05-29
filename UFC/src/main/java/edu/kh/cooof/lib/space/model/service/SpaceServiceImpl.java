package edu.kh.cooof.lib.space.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.cooof.lib.space.model.dto.SpaceDTO;
import edu.kh.cooof.lib.space.model.mapper.SpaceMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class SpaceServiceImpl implements SpaceService {

	@Autowired
	private SpaceMapper mapper;

	// 공간 편집 저장하기
	@Transactional
	public int saveSpaceManagement(List<SpaceDTO> spaceList) {

		// space테이블의 기존 정보 지우기
		mapper.delTbSpace();

		// 편집 내용 저장하기
		int totalInserted = 0;
		for (SpaceDTO space : spaceList) {
			totalInserted += mapper.saveManagement(space);
		}
		return totalInserted;
	}

	// 관리자 : 공간의 avail 수정하기
	@Override
	public String updateSpaceStatus(int spaceNo, int status) {
		Map<String, Object> params = new HashMap<>();
		params.put("spaceNo", spaceNo);
		params.put("changAvailCode", status);
		int updateCount = mapper.updateSpaceStatus(params);

		if (updateCount > 0) {
			return "성공적으로 업데이트되었습니다.";
		} else {
			return "업데이트에 실패했습니다.";
		}
	}

	// 저장된 공간 정보 불러오기
	@Override
	public List<SpaceDTO> getAllSpaces() {

		return mapper.getAllSpaces();
	}

	// 공간 이용하기
	@Override
	public int useSpace(int memberNo, int spaceNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("memberNo", memberNo);
		params.put("spaceNo", spaceNo);
		return mapper.useSpace(params);
	}

	// 공간 그만 이용하기
	@Override
	public int stopUsingSpace(int memberNo, int curUsingSpaceNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("memberNo", memberNo);
		params.put("curUsingSpaceNo", curUsingSpaceNo);

		int result1 = mapper.stopUsingSpace(params);
		if (result1 != 1) {
			System.out.println("그만 이용하기에서 실패");
		}
		int result2 = mapper.deleteRentSpace(params);
		if (result2 != 1) {
			System.out.println("기록 지우기에서 실패");
		}

		return result1 + result2;
	}

	// 공간 예약하기를 위한
	private static final int RESERVATION_SUCCESS = 1;
	private static final int RESERVATION_FAILURE = 0;
	private static final int RESERVATION_ERROR = 2;

	// 공간 예약하기
	@Override
	 public int bookSpace(int memberNo, int spaceNo, String startTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberNo", memberNo);
        params.put("spaceNo", spaceNo);
        params.put("startTime", startTime);

        log.debug("Attempting to book space with params: {}", params);

        try {
            Integer reservationSpaceResult = mapper.bookSpace(params);
            log.debug("Reservation space result: {}", reservationSpaceResult);
            if (reservationSpaceResult != null && reservationSpaceResult == RESERVATION_SUCCESS) {
                log.debug("Booking space succeeded");
                return RESERVATION_SUCCESS;
            } else if (reservationSpaceResult != null && reservationSpaceResult == RESERVATION_FAILURE) {
                log.debug("Booking space failed: space already booked");
                return RESERVATION_FAILURE;
            }
        } catch (Exception e) {
            log.error("Error while booking space", e);
        }
        return RESERVATION_ERROR;
    }

    @Override
    public int checkOtherReservation(int spaceNo, String startTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("spaceNo", spaceNo);
        params.put("startTime", startTime);

        log.debug("Checking other reservations with params: {}", params);

        try {
            Integer result = mapper.checkOtherReservation(params);
            log.debug("Check other reservations result: {}", result);
            return result != null ? result : RESERVATION_FAILURE;
        } catch (Exception e) {
            log.error("Error while checking other reservations", e);
            return RESERVATION_ERROR;
        }
    }

    @Override
    public int checkStartTime(int spaceNo, String startTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("spaceNo", spaceNo);
        params.put("startTime", startTime);

        log.debug("Checking start time with params: {}", params);

        try {
            Integer checkTime = mapper.checkStartTime(params);
            log.debug("Check start time result: {}", checkTime);
            if (checkTime != null && checkTime == RESERVATION_SUCCESS) {
                log.debug("Start time check succeeded");
                return RESERVATION_SUCCESS;
            } else if (checkTime != null && checkTime == RESERVATION_FAILURE) {
                log.debug("Start time check failed: time conflict");
                return RESERVATION_FAILURE;
            }
        } catch (Exception e) {
            log.error("Error while checking start time", e);
        }
        return RESERVATION_ERROR;
    }
}
