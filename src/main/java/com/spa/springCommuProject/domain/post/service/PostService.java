package com.spa.springCommuProject.domain.post.service;


import com.spa.springCommuProject.domain.post.entity.FreePost;
import com.spa.springCommuProject.domain.post.entity.PhotoPost;
import com.spa.springCommuProject.domain.post.entity.Post;
import com.spa.springCommuProject.domain.post.entity.VideoPost;
import com.spa.springCommuProject.domain.post.repository.PostRepository;
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

    public List<Post> findAvailablePosts(){
        return postRepository.findAvailableAll();
    }

    public List<FreePost> findAvailableFreePosts(){
        return postRepository.findAvailableAllFreePosts();
    }

    public List<FreePost> findAvailablePagingFreePosts(int page){
        return postRepository.findAvailablePagingFreePosts(page);
    }

    public List<FreePost> mainPageFreePosts(){
        return postRepository.mainPageFreePosts();
    }

    public List<PhotoPost> findAvailablePhotoPosts(){
        return postRepository.findAvailableAllPhotoPost();
    }

    public List<PhotoPost> findAvailableAllPhotoPostViewDesc(){
        return postRepository.findAvailableAllPhotoPostViewDesc();
    }

    public List<PhotoPost> findAvailablePagingPhotoPosts(int page){
        return postRepository.findAvailablePagingPhotoPosts(page);
    }

    public List<VideoPost> findAvailableVideoPosts(){
        return postRepository.findAvailableAllVideoPost();
    }

    public List<VideoPost> findAvailablePagingVideoPosts(int page){
        return postRepository.findAvailablePagingVideoPosts(page);
    }

    public List<VideoPost> mainPageVideoPosts(){
        return postRepository.mainPageVideoPosts();
    }

    public List<Post> findAllPosts(){
        return postRepository.findAll();
    }

    @Transactional
    public void updatePost(Long postId, String title, String content) {
        Post findPost = postRepository.findOnePost(postId);
        findPost.update(title,content);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post findPost = postRepository.findOnePost(postId);
        findPost.delete();
    }

    @Transactional
    public void viewIncrease(Long postId){
        Post findPost = postRepository.findOnePost(postId);
        findPost.viewIncrease();
    }
}
