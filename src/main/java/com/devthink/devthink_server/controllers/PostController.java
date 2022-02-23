package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CategoryService;
import com.devthink.devthink_server.application.PostService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.domain.Category;
import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.PostListData;
import com.devthink.devthink_server.dto.PostRequestData;
import com.devthink.devthink_server.dto.PostResponseData;
import com.devthink.devthink_server.security.UserAuthentication;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CategoryService categoryService;

    /**
     * 페이지를 요청하면 카테고리별 페이지의 게시글을 가져옵니다.
     * [GET] /posts/list/:categoryId
     * @return List<PostListData> 카테고리별 게시글 리스트
     */
    @GetMapping("/list/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "카테고리별 게시글 리스트 조회", notes = "게시글 전체 리스트를 전달된 카테고리별로 조회합니다.")
    public List<PostListData> list(@PathVariable("categoryId") Long categoryId) {
        return postService.getPosts(categoryId);
    }

    /**
     * 게시글 상세 조회 API
     * [GET] /posts/:id
     * @param id 게시글의 조회 아이디
     * @return Id인 게시글
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "게시글 검색", notes = "게시글의 id를 검색하여 게시글을 가져옵니다.")
    public PostResponseData getPost(@PathVariable("id") Long id) {
        Post post = postService.getPostById(id);
        return post.toPostResponseData();
    }

    /**
     * 게시글 등록 API
     * [POST] /posts/write
     * @param postRequestData 게시글의 정보(userId, categoryId, title, content)
     * @return PostResponseData (새로 생성된 게시글)
     */
    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "게시글 등록", notes = "카테고리별 게시글을 등록합니다.")
    @PreAuthorize("isAuthenticated()")
    public PostResponseData write(@RequestBody @Valid PostRequestData postRequestData,
                                  UserAuthentication userAuthentication
    ) throws AccessDeniedException {
        Long userId = userAuthentication.getUserId();
        User user = userService.getUser(userId);
        Category category = categoryService.getCategory(postRequestData.getCategoryId());
        Post post = postService.savePost(user, category, postRequestData);
        return post.toPostResponseData();
    }

    /**
     * 게시글 제목,내용 수정 API
     * [PUT] /posts/:id
     * @param id (수정할 게시글 아이디)
     * @param postRequestData (수정할 게시글 내용)
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "게시글 수정", notes = "식별자 값과 게시글의 정보를 받아, 게시글을 입력한 정보로 변경합니다.")
    @PreAuthorize("isAuthenticated()")
    public PostResponseData update(@PathVariable("id") Long id,
                                   @RequestBody @Valid PostRequestData postRequestData,
                                   UserAuthentication userAuthentication
    ) throws AccessDeniedException {
        Long userId = userAuthentication.getUserId();
        User user = userService.getUser(userId);
        Post post = postService.getPostById(id);

        Post updatedPost = postService.update(user, post, postRequestData);
        return updatedPost.toPostResponseData();
    }

    /**
     * 게시글 삭제 API
     * [DELETE] /posts/:id
     * @param id (삭제할 게시글 아이디)
     * @return PostResponseData (삭제한 게시글)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "게시글 삭제", notes = "입력한 게시글의 식별자 값을 받아 게시글을 삭제합니다.")
    @PreAuthorize("isAuthenticated()")
    public void deletePost(@PathVariable("id") Long id,
                                       UserAuthentication userAuthentication
    ) throws AccessDeniedException {
        Long userId = userAuthentication.getUserId();
        User user = userService.getUser(userId);
        Post post = postService.getPostById(id);

        postService.deletePost(user, post);
    }

    /**
     * 카테고리별 제목 검색 API
     * [GET] /posts/search/:categoryId?keyword=제목
     * @param categoryId 카테고리 아이디
     * @param keyword 검색 제목
     * @return 제목이 담긴 게시글
     */
    @GetMapping("/search/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "카테고리별 게시글 검색", notes = "사용자로부터 제목을 받아, 카테고리별 제목이 담긴 게시글을 반환합니다.")
    public List<PostResponseData> search(@PathVariable("categoryId") Long categoryId, @RequestParam String keyword) {
        return postService.search(categoryId, keyword);
    }

    /**
     * 카테고리별 베스트 게시글 가져오기 API
     * [GET] /posts/best/:categoryId?page=? 정렬방식
     * @param categoryId 카테고리 아이디
     * @return PostResponseData 게시글
     */
    @GetMapping("/best/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "카테고리별 베스트 게시글 가져오기", notes = "사용자로부터 카테고리 id를 받아, 베스트 게시글을 가져옵니다.")
    public List<PostResponseData> searchBest(@PathVariable("categoryId") Long categoryId) {
        Category category = categoryService.getCategory(categoryId);
        return postService.getBestPost(category);
    }

    /**
     * 게시글 신고 API
     * [PUT] /posts/report/:id
     * @param postId 게시글 아이디
     * @return String 신고당한 유저 아이디
     */
    @PutMapping("/report/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "게시글 신고", notes = "게시글을 신고합니다.")
    @PreAuthorize("isAuthenticated()")
    public void report(@PathVariable("postId") Long postId,
                         UserAuthentication userAuthentication
    ) throws AccessDeniedException {
        Long userId = userAuthentication.getUserId();
        User user = userService.getUser(userId);
        Post post = postService.getPostById(postId);
        User reportUser = userService.getUser(post.getUser().getId());
        postService.report(user, post, reportUser);
    }

}
