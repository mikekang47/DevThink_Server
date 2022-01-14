package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/posts")
/**
 * 사용자의 HTTP 요청을 처리하는 클래스입니다.
 */

// 1. 글 작성 -> POST /posts/write
// 2. 글 id로 검색 -> GET /posts/{id}
// 3. 글 업데이트 -> PUT /posts/{id}
// 4. 글 삭제 -> DELETE /posts/{id}
// 5. 페이지 가져오기 -> GET /posts?page=숫자
// ----> 기본 1페이지, 6개의 게시글 가져오기, 최신 날짜순이 기본
// 6. 키워드로 제목 검색 -> GET /posts/search?keyword=문자열
// ----> 문자열이 제목인 게시글 가져오기

public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 게시글에서 페이지를 요청하면 몇 페이지의 게시글을 가져옵니다.
     * @param page 사용자가 요청한 페이지 수
     * @return 몇 페이지의 게시글 (기본값 6개의 게시글)
     */
    @GetMapping
    public List<Post> list(@RequestParam(value="page", defaultValue = "1") int page){
        return postService.list(page); // 불러올 페이지는 1부터 시작
    }

    /**
     * 게시글의 id를 검색하여 게시글을 가져옵니다.
     * @param id 게시글의 고유 id값
     * @return id의 게시글
     */
    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable Long id){
        return postService.getPost(id);

    }

    /**
     * 입력한 게시글의 정보(유저 id, 카테고리 id, 제목, 내용, status)를 입력받아 게시글을 생성합니다.
     * @param postDto 입력한 vaild한 게시글의 정보
     * @return  게시글 생성
     */
    @PostMapping("/write")
    public Post write(@RequestBody PostDto postDto){
        return postService.savePost(postDto);
    }

    /**
     * 입력한 게시글의 식별자 값과 valid한 게시글의 정보를 받아, 기존의 게시글을 입력한 정보로 변경합니다.
     * @param id 게시글의 식별자
     * @param postDto 제목, 내용, status
     * @return 기존 게시글의 정보 수정
     */

    @PutMapping("/{id}")
    public Post update(@PathVariable Long id, @RequestBody PostDto postDto){
        return postService.update(id, postDto);
    }

    /**
     * 입력한 게시글의 식별자 값을 받아 게시글을 삭제합니다.
     * @param id 게시글의 식별자
     */
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id)
    {
        postService.deletePost(id);
    }

    /**
     * keyword를 검색하여, keyword가 담긴 제목의 게시글을 탐색합니다.
     * @param keyword 검색하는 제목
     * @return keyword가 담긴 제목의 게시글
     */

    @GetMapping("/search")
    public List<Post> search(@RequestParam String keyword)
    {
        return postService.search(keyword);
    }

}
