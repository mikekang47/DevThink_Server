package com.devthink.devthink_server.service;

import com.devthink.devthink_server.infra.LetterRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class LetterService {

    private final LetterRepository letterRepository;
    private final Mapper mapper;

    public LetterService(LetterRepository letterRepository, Mapper mapper) {
        this.letterRepository = letterRepository;
        this.mapper = mapper;
    }

    /**
     * 메시지 작성
     * @param dto
     */
    public void addMessage(MessageDto dto){

    }

}