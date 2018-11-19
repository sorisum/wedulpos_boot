package com.wedul.wedulpos.message.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.wedul.wedulpos.message.dto.ChatMessage;
import com.wedul.wedulpos.message.service.MessageService;

/**
 * 메시지 컨트롤러 
 * 
 * @author jeongcheol
 *
 */
@Controller
@AllArgsConstructor
public class MessageController {
	
	// @EnableWebSocketMessageBroker를 통해서 등록되는 bean이다. 특정 Broker로 메시지를 전달한다.
	private final SimpMessagingTemplate template;
	private final MessageService messageService;

    /**
     * //Client가 SEND를 할 수 있는 경로다. StompWebSocketConfig에서 
	 * //등록한 applicationDestinationPrfixes와 @MessageMapping의 경로가 합쳐진다.(/publish/chat/join)
     * 
     * 수산자에게 메시지 전송 
     * 
     * @param message
     */
    @MessageMapping("/chat/send") 
    public void sendMessage(ChatMessage message) {
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

    /**
     * writer가 전송한 메시지를 수신자에게 전송 
     * 
     * @param message
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

}
