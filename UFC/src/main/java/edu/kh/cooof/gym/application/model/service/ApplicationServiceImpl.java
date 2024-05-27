package edu.kh.cooof.gym.application.model.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.cooof.gym.application.model.dto.Application;
import edu.kh.cooof.gym.application.model.mapper.ApplicationMapper;
import edu.kh.cooof.member.model.dto.Member;
import edu.kh.cooof.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    
    private final ApplicationMapper mapper;
    
    @Override
    public int apply(Application inputApply) {
    	
    	return mapper.insertApplication(inputApply);
    }
    	

}
