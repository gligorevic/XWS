package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.Message;
import com.example.AgentApplication.domain.Request;
import com.example.AgentApplication.domain.User;
import com.example.AgentApplication.dto.MessageDTO;
import com.example.AgentApplication.exception.CustomException;
import com.example.AgentApplication.repository.MessageRepository;
import com.example.AgentApplication.repository.RequestRepository;
import com.example.AgentApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    public Message sendMessage(MessageDTO messageDTO) throws CustomException {
        Message message = new Message(messageDTO);

        Request request = requestRepository.findRequestById(messageDTO.getRequestId());
        if(request == null)
            throw new CustomException("Request doesn't exist.", HttpStatus.BAD_REQUEST);
        message.setRequest(request);

        User sentBy = userRepository.getOne(messageDTO.getSentBy());
        if(sentBy == null)
            throw new CustomException("User who sent message doesn't exist.", HttpStatus.BAD_REQUEST);
        message.setSentBy(sentBy);

        User reciever = userRepository.findByEmail(request.getAdvertisement().getCar().getUserEmail());
        if(reciever == null)
            throw new CustomException("User reciever doesn't exist.", HttpStatus.BAD_REQUEST);
        message.setReciever(reciever);

        return messageRepository.save(message);
    }

    public List<Message> getMessagesByReciever(String email){
        return messageRepository.getMessagesByReciever(email);
    }

    public List<Message> getMessagesByRequest(Long id){
        return messageRepository.getMessagesByRequest(id);
    }

}
