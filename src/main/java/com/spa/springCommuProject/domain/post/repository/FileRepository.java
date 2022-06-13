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

    public List<Image> findImagesByPostId(Long postId){
        return em.createQuery("select i from Image i", Image.class).getResultList().stream().filter(x->x.getPost().getId().equals(postId)).collect(Collectors.toList());
    }

    public Image findOneImageByPostId(Long postId){
        List<Image> images = findImagesByPostId(postId);
        if(images.isEmpty()){
            return new Image("없는 파일","없는 파일");
        }
        return images.get(0);
    }

    public List<Video> findVideosByPostId(Long postId){
        return em.createQuery("select v from Video v", Video.class).getResultList().stream().filter(x->x.getPost().getId().equals(postId)).collect(Collectors.toList());
    }
}
