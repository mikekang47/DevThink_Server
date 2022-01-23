package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Like;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @GetMapping
    public Like detail() {
        return null;
    }
}
