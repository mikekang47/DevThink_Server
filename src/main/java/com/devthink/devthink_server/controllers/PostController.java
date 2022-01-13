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

    /**
     * 페이징 처리하여 글 가져오기
     * @param page
     * @return
     */
    @GetMapping
    public List<Post> list(@RequestParam(value="page", defaultValue = "1") int page){
        return postService.list(page); // 불러올 페이지는 1부터 시작
    }

    /**
     * 글 id로 검색
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable Long id){
        return postService.getPost(id);

    }

    /**
     * 게시글 쓰기(저장)
     * @param postDto
     * @return
     */
    @PostMapping("/write")
    public Post write(@RequestBody PostDto postDto){
        return postService.savePost(postDto);
    }

    /**
     * 글 업데이트
     * @param id
     * @param postDto
     * @return
     */

    @PutMapping("/{id}")
    public Post update(@PathVariable Long id, @RequestBody PostDto postDto){
        return postService.update(id, postDto);
    }

    /**
     * 글 삭제
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id)
    {
        postService.deletePost(id);
    }

    /**
     * 키워드로 검색
     */

    @GetMapping("/search")
    public List<Post> search(@RequestParam String keyword)
    {
        return postService.search(keyword);
    }

}
