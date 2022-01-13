package com.devthink.devthink_server.controller;

import com.devthink.devthink_server.services.posts.PostsService;
import com.devthink.devthink_server.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor    //Bean 주입
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @GetMapping(value="/")
    public String root() {
        return "index";
    }
    @GetMapping(value="/index")
    public String index() {
        return "index";
    }
    @GetMapping(value="/board")
    public String board() {
        return "board";
    }

    @PostMapping("/saveForm/post")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }
}
