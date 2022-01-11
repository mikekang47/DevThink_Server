package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.service.PostService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public List<PostDto> list(){
        return PostService.getPostlist();
    }

    @GetMapping("/{id}")
    public PostDto findById(@PathVariable Long id){
        return PostService.getPost(id);

    }

    @PostMapping("/write")
    public PostDto write(@RequestBody PostDto postDto){
        postService.savePost(postDto);
        return postDto;
    }

}