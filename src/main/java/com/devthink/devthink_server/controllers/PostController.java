package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> list(@RequestParam(value="page", defaultValue = "1") Integer pageNum){
        List<Post> postList = postService.getPostList(pageNum);
        Integer[] pageList = postService.getPageList(pageNum);

        for (Integer integer : pageList) {
            System.out.println("integer = " + integer);
        }
        return postList;

    }

    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable Long id){
        return postService.getPost(id);

    }

    @PostMapping("/write")
    public Post write(@RequestBody PostDto postDto){
        return postService.savePost(postDto);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable Long id, @RequestBody PostDto postDto){
        return postService.update(id, postDto);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id)
    {
        postService.deletePost(id);
    }

    @GetMapping("/search")
    public List<Post> search(@RequestParam(value = "keyword") String keyword) {
        return postService.searchPosts(keyword);
    }

}
