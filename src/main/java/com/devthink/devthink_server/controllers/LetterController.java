package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.service.LetterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 메시지 전송 코드
@RestController
@RequestMapping("/messages")
public class LetterController {

    private LetterService letterService;

    public LetterController(LetterService letterService) {
        this.letterService = letterService;
    }

    @PostMapping
    public LetterDto addMessage(@RequestBody LetterDto letterDto)
    {

    }
}