package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.infra.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostRepository postRepository;

    @PostMapping("/write")
    public Post write(@RequestBody Post post){
        postRepository.save(post);
        return post;
    }

}