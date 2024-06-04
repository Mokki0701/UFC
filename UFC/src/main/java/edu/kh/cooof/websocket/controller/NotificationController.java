package edu.kh.cooof.websocket.controller;

import org.springframework.stereotype.Controller;

import edu.kh.cooof.websocket.model.service.NotificationService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService service;
	
}
