package com.spa.springCommuProject.domain.post;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long savePost(Post post){
        postRepository.save(post);
        return post.getId();
    }

    public Post findOnePost(Long postId){
        return postRepository.findOnePost(postId);
    }

    public List<Post> findPosts(){
        return postRepository.findAll();
    }

    @Transactional
    public void updateFreePost(Long postId, String title, String content) {
        Post findPost = postRepository.findOnePost(postId);
        findPost.update(title,content);
    }

    @Transactional
    public void deleteFreePost(Long postId) {
        Post findPost = postRepository.findOnePost(postId);
        findPost.delete();
    }

    @Transactional
    public void viewIncrease(Long postId){
        Post findPost = postRepository.findOnePost(postId);
        findPost.viewIncrease();
    }
}
