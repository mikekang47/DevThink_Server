package com.devthink.devthink_server.service;

import com.devthink.devthink_server.domain.Post;
import com.devthink.devthink_server.dto.PostDto;
import com.devthink.devthink_server.infra.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private PostRepository postRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 수
    private static final int PAGE_POST_COUNT = 4; // 한 페이지에 존재하는 게시글 수

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(PostDto postDto){
        return postRepository.save(postDto.toEntity());

    }
    public List<Post> getPostList(){
        return postRepository.findAll();

    }

    public List<Post> getPostList(Integer pageNum){
        Page<Post> page = postRepository.findAll(PageRequest
                .of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "create_at")));

        List<Post> posts = page.getContent();

        return posts;

    }

    public Integer[] getPageList(Integer curPageNum){
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        Double postsTotal = Double.valueOf(postRepository.count());

        Integer totalLastNum = (int)(Math.ceil((postsTotal/PAGE_POST_COUNT)));

        Integer blockLastPage = (totalLastNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastNum;

        curPageNum = (curPageNum<=3) ? 1 : curPageNum-2;

        for(int val=curPageNum, i=0; val<=blockLastPage; val++, i++){
            pageList[i] = val;
        }
        return pageList;

    }

    public Optional<Post> getPost(Long id){
        return postRepository.findById(id);
    }

    public Post update(Long id, PostDto postDto){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시물이 없습니다. " + id));

        post.update(postDto.getTitle(), postDto.getContent());
        return postRepository.save(post);

    }

    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

    public List<Post> searchPosts(String keyword){
        return postRepository.findByTitleContaining(keyword);
    }
}
