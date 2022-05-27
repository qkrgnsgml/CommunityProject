package com.spa.springCommuProject.domain.post.repository;

import com.spa.springCommuProject.domain.post.entity.Image;
import com.spa.springCommuProject.domain.post.entity.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FileRepository {
    private final EntityManager em;

    public void save(Image image) {
        em.persist(image);
    }

    public void save(Video video) {
        em.persist(video);
    }

    public List<Image> findImagesbyPostId(Long postId){
        return em.createQuery("select i from Image i", Image.class).getResultList().stream().filter(x->x.getPost().getId()==postId).collect(Collectors.toList());
    }

    public List<Video> findVideosbyPostId(Long postId){
        return em.createQuery("select v from Video v", Video.class).getResultList().stream().filter(x->x.getPost().getId()==postId).collect(Collectors.toList());
    }
}
