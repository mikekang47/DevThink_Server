package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.service.PostService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/write")
    public PostDto write(@RequestBody PostDto postDto){
        postService.savePost(postDto);
        return postDto;
    }

}