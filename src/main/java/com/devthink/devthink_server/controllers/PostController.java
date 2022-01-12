package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> list(){
        return postService.getPostList();
    }

    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable Long id){
        return postService.getPost(id);

    }

    @PostMapping("/write")
    public Post write(@RequestBody PostDto postDto){
        return postService.savePost(postDto);
    }

}
