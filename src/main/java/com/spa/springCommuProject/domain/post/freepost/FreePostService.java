package com.spa.springCommuProject.domain.post.freepost;

import com.spa.springCommuProject.domain.post.Post;
import com.spa.springCommuProject.domain.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FreePostService implements PostService {

    private final FreePostRepository freePostRepository;


    @Override
    public Long join(Post freePost) {
        freePostRepository.save(freePost);
        return freePost.getId();
    }


    @Override
    public Post findOnePost(Long postId) {
        return freePostRepository.findOnePost(postId);
    }
}
