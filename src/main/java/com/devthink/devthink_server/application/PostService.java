package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.*;
import com.devthink.devthink_server.dto.PostListData;
import com.devthink.devthink_server.dto.PostRequestData;
import com.devthink.devthink_server.dto.PostResponseData;
import com.devthink.devthink_server.errors.PostReportAlreadyRequestException;
import com.devthink.devthink_server.errors.PostReportBadRequestException;
import com.devthink.devthink_server.errors.UserNotMatchException;
import com.devthink.devthink_server.errors.PostNotFoundException;
import com.devthink.devthink_server.infra.PostReportRepository;
import com.devthink.devthink_server.infra.PostRepository;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostReportRepository postReportRepository;
    private final Mapper mapper;

    public PostService(PostRepository postRepository, PostReportRepository postReportRepository, Mapper mapper) {
        this.postRepository = postRepository;
        this.postReportRepository = postReportRepository;
        this.mapper = mapper;
    }


    /**
     * 새로운 게시글을 DB에 저장합니다.
     *
     * @param postRequestData 게시글 데이터
     * @return Post 생성된 게시글
     */
    public Post savePost(User user, Category category, PostRequestData postRequestData) {
        boolean imageCheck;
        if (postRequestData.getImageUrl().isEmpty())
            imageCheck = false;
        else
            imageCheck = true;

        Post post = postRepository.save(
                Post.builder()
                        .title(postRequestData.getTitle())
                        .content(postRequestData.getContent())
                        .subTitle(postRequestData.getSubTitle())
                        .category(category)
                        .user(user)
                        .imageUrl(postRequestData.getImageUrl())
                        .image(imageCheck)
                        .build()
        );
        return post;
    }

    /**
     * 전체 게시글을 조회합니다.
     *
     * @return List<Post> 조회한 전체 게시글
     */
    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    /**
     * 게시글을 최신순으로(게시글 id별 내림차순) 전체 반환합니다.
     *
     * @param categoryId 카테고리 아이디
     * @return List<PostListData> 조회된 게시글
     */
    public List<PostListData> getPosts(Long categoryId) {
        List<Post> postPage = postRepository.findByDeletedIsFalseOrderByIdDesc(categoryId);
        return postPage.stream()
                .map(Post::toPostListData)
                .collect(Collectors.toList());

    }

    /**
     * 게시글을 DB에 찾고 없으면 에러를 반환합니다.
     *
     * @param id 찾고자 하는 게시글의 식별자
     * @return Post 찾았을 경우 게시글을 반환, 찾지 못하면 에러 반환
     */
    public Post getPostById(Long id) {
        return postRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    /**
     * 게시글의 내용과 제목을 업데이트합니다.
     *
     * @param post            게시글
     * @param postRequestData 업데이트한 게시글 내용
     */
    public Post update(User user, Post post, PostRequestData postRequestData) {
        checkMatchUser(post, user.getId());
        post.update(postRequestData.getSubTitle(), postRequestData.getTitle(), postRequestData.getContent());
        return post;
    }

    /**
     * 게시글을 삭제합니다.
     *
     * @param post 삭제할 게시글
     * @return Post 삭제된 게시글
     * @Param User 삭제하는 유저
     */
    public void deletePost(User user, Post post) {
        getPostById(post.getId());
        checkMatchUser(post, user.getId());
        post.setDeleted(true);
    }

    /**
     * 제목이 담긴 게시글을 반환합니다.
     *
     * @param keyword    찾는 제목
     * @param categoryId 카테고리 아이디
     * @return List<PostResponseData> 게시글 정보
     */
    public List<PostResponseData> search(Long categoryId, String keyword) {
        List<Post> posts = postRepository.findByCategory_IdAndTitleContainingAndDeletedIsFalseOrderByIdDesc(categoryId, keyword);
        return posts.stream()
                .map(Post::toPostResponseData)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리의 베스트 게시글을 가져옵니다.
     *
     * @param category 카테고리
     * @return List<PostResponseData> 베스트 게시글 정보
     */
    public List<PostResponseData> getBestPost(Category category) {
        LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.now();
        List<Post> bestPost = postRepository.getBestPost(category.getId(), start, end, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "heartCnt")));
        return bestPost.stream()
                .map(Post::toPostResponseData)
                .collect(Collectors.toList());
    }

    /**
     * 게시글을 신고합니다.
     *
     * @param user       신고한 유저 정보
     * @param post       신고할 게시글
     * @param reportUser 게시글 작성자
     * @return String 신고된 게시글 번호
     */
    public void report(User user, Post post, User reportUser) {
        checkReportUser(user, reportUser);
        checkPostReport(user, post);
        reportUser.setReported();
        postReportRepository.save(
                PostReport.builder()
                        .post(post)
                        .user(user)
                        .build()
        );
    }

    /**
     * 게시글 작성자와 로그인 유저가 같은지 확인합니다.
     *
     * @param post        (게시글)
     * @param loginUserId (로그인 사용자 식별자)
     */
    private void checkMatchUser(Post post, Long loginUserId) {
        if (post.getUser().getId() != loginUserId) {
            throw new UserNotMatchException();
        }
    }

    /**
     * 게시글 작성자와 신고한 유저가 같은지 확인합니다.
     *
     * @param user       게시글 작성자
     * @param reportUser (신고한 사용자)
     */
    private void checkReportUser(User user, User reportUser) {
        if (user.getId() == reportUser.getId()) {
            throw new PostReportBadRequestException();
        }
    }

    /**
     * 해당 유저가 해당 게시글을 신고한 기록이 있는지 확인합니다.
     *
     * @param user 게시글을 신고한 유저
     * @param post 유저가 신고한 게시글
     */
    private void checkPostReport(User user, Post post) {
        if (postReportRepository.existsPostReport(user.getId(), post.getId())) {
            throw new PostReportAlreadyRequestException();
        }
    }

}
