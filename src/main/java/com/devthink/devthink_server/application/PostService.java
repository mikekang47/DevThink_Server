package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Category;
import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.domain.PostReport;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.PostListData;
import com.devthink.devthink_server.dto.PostRequestData;
import com.devthink.devthink_server.dto.PostResponseData;
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
     * 해당 유저가 해당 게시글을 신고한 기록이 있는지 확인합니다.
     *
     */
    public boolean checkPostReport(User user, Post post){
        return postReportRepository.existsPostReport(user.getId(), post.getId());
    }

    /**
     * 새로운 게시글을 DB에 저장합니다.
     * @param postRequestData 게시글 데이터
     * @return Post 생성된 게시글
     */
    public Post savePost(User user, Category category, PostRequestData postRequestData){
        boolean imageCheck;
        if(postRequestData.getImageUrl().isEmpty())
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
     * @return List<Post> 조회한 전체 게시글
     */
    public List<Post> getPostList(){
        return postRepository.findAll();
    }

    /**
     * page에 해당하는 게시글을 반환합니다.
     * @param categoryId 카테고리 아이디
     * @param pageable 페이지 정보
     * @return List<PostListData> 조회된 게시글
     */
    public List<PostListData> getPosts(Long categoryId, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "id"));

        List<Post> postPage = postRepository.findByDeletedIsFalse(categoryId, pageable);
        return postPage.stream()
                .map(Post::toPostListData)
                .collect(Collectors.toList());
    }

    /**
     * 게시글을 DB에 찾고 없으면 에러를 반환합니다.
     * @param id 찾고자 하는 게시글의 식별자
     * @return Post 찾았을 경우 게시글을 반환, 찾지 못하면 에러 반환
     */
    public Post getPostById(Long id){
        return postRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    /**
     * 게시글의 내용과 제목을 업데이트합니다.
     * @param post 게시글
     * @param postRequestData 업데이트한 게시글 내용
     */
    public Post update(Post post, PostRequestData postRequestData){
        post.update(postRequestData.getSubTitle(), postRequestData.getTitle(), postRequestData.getContent());
        return post;
    }

    /**
     * 게시글을 삭제합니다.
     * @param post 삭제할 게시글
     * @return Post 삭제된 게시글
     */
    public Post deletePost(Post post){
        getPostById(post.getId());
        post.setDeleted(true);
        return post;
    }

    /**
     * 제목이 담긴 게시글을 반환합니다.
     * @param keyword 찾는 제목
     * @param categoryId 카테고리 아이디
     * @return List<PostResponseData> 게시글 정보
     */
    public List<PostResponseData> search(Long categoryId, String keyword, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "id"));
        List<Post> posts = postRepository.findByCategory_IdAndTitleContainingAndDeletedIsFalse(categoryId, keyword, pageable).getContent();
        return posts.stream()
                .map(Post::toPostResponseData)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리의 베스트 게시글을 가져옵니다.
     * @param category 카테고리
     * @return List<PostResponseData> 베스트 게시글 정보
     */
    public List<PostResponseData> getBestPost(Category category){
        LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0, 0, 0));
        LocalDateTime end = LocalDateTime.now();
        List<Post> bestPost = postRepository.getBestPost(category.getId(), start, end, PageRequest.of(0,1, Sort.by(Sort.Direction.DESC, "heartCnt")));
        return bestPost.stream()
                .map(Post::toPostResponseData)
                .collect(Collectors.toList());
   }

    /**
     * 게시글을 신고합니다.
     * @param user 게시글 작성자
     * @return String 신고된 게시글 번호
     */
    public String report(User user, Post post, User reportUser){
        reportUser.setReported();
        PostReport postReport = PostReport.builder()
                .post(post)
                .user(user)
                .build();
        postReportRepository.save(postReport);
        return reportUser.getId().toString();
    }

}
