package com.devthink.devthink_server.controller;

import com.devthink.devthink_server.application.PostsService;
import com.devthink.devthink_server.domain.posts.Posts;
import com.devthink.devthink_server.dto.posts.PostsResponseDto;
import com.devthink.devthink_server.dto.posts.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor    //Bean 주입
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/saveForm/post")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @GetMapping("/post")
    public List<Posts> list(){
        List<Posts> postList = postsService.list();
        return postList;
    }
}
