package edu.kh.cooof.websocket.model.service;

import org.springframework.stereotype.Service;

import edu.kh.cooof.websocket.model.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationMapper mapper;
	
}
